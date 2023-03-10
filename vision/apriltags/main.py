import cv2
from dt_apriltags import Detector
import yaml
import argparse
import os
import util.nt_util as nt_util
import util.util as util
import math
import numpy as np
import ntcore

# XX: xexanoth, YS: yog-sothoth
ABSOLUTE_POSITION_X_TOPIC = "XXAbsolutePositionX" # "YSAbsolutePositionX"
ABSOLUTE_POSITION_Y_TOPIC = "XXAbsolutePositionY" # "YSAbsolutePositionY"
ABSOLUTE_POSITION_Z_TOPIC = "XXAbsolutePositionZ" # "YSAbsolutePositionZ"

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("filename")
    args = parser.parse_args()

    nt_inst = ntcore.NetworkTableInstance.getDefault()
    nt_inst.setServer("10.36.56.2")
    nt_inst.startClient4("xexanoth")
    table = nt_inst.getTable("SmartDashboard")

    x_distance_pub = nt_util.DoublePublisher(table, "ApriltagDistanceX")
    z_distance_pub = nt_util.DoublePublisher(table, "ApriltagDistanceZ")
    x_position_pub = nt_util.DoublePublisher(table, ABSOLUTE_POSITION_X_TOPIC)
    y_position_pub = nt_util.DoublePublisher(table, ABSOLUTE_POSITION_Y_TOPIC)
    z_position_pub = nt_util.DoublePublisher(table, ABSOLUTE_POSITION_Z_TOPIC)

    if not os.path.exists(args.filename):
        print("Invalid camera parameter file.")
        return

    camera_params = [0] * 4 # fx, fy, cx, cy

    with open(args.filename) as f:
        params = yaml.safe_load(f)["pinhole"]
        camera_params[0] = params["fx"]
        camera_params[1] = params["fy"]
        camera_params[2] = params["cx"]
        camera_params[3] = params["cy"]

    cap = cv2.VideoCapture(0)
    # cap2 = cv2.VideoCapture(1)"YSAbsolutePositionX"

    at_detector = Detector(searchpath=['apriltags'],
                families='tag16h5',
                nthreads=2,
                quad_sigma=0.2,
                decode_sharpening=0.25,
            )
    
    current_frame = 0
    frame_buffer = 5
    detected_tags = {}
    validated = []
    previous_frame = None

    def valid_tag(id):
        return (detected_tags[id] and False not in detected_tags[id])


    def result_callback(tag, frame):
        tag_id = tag.tag_id
        if tag_id not in detected_tags.keys():
            detected_tags[tag_id] = [False] * frame_buffer

        detected_tags[tag_id][current_frame] = True

        if tag_id not in validated and not valid_tag(tag_id): return

        for c in range(4):
            toI = c + 1
            if(c == 3): toI = 0

            original = tag.corners[c]
            to = tag.corners[toI]
            
            cv2.line(frame, (int(original[0]), int(original[1])), (int(to[0]), int(to[1])), (0, 255, 0), 2)

        
    previous_pos = None
    while True:
        _, frame = cap.read()

        image = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        tags = list(filter(lambda tag: tag.hamming < 1, at_detector.detect(image, estimate_tag_pose=True, camera_params=camera_params, tag_size=0.1397)))

        if len(tags) == 0:
            continue

        # For now, we're just going to get the distance to the first tag found and publish that
        first_tag = tags[0]
        x_distance_pub.publish(first_tag.pose_t[0])
        z_distance_pub.publish(first_tag.pose_t[2])

        abs_pos_possible = []
        for tag in tags:
            if int(tag.tag_id) < 1 or int(tag.tag_id) > 8:
                continue

            result_callback(tag, frame)

            center = tag.center
            centerCord = (int(center[0]), int(center[1]))
            cv2.putText(frame, "dX: " + str(tag.pose_t[0]), (int(center[0]), int(center[1])), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 0), 2)
            cv2.putText(frame, "dY: " + str(tag.pose_t[1]), (int(center[0]), int(center[1]) + 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 0), 2)
            cv2.putText(frame, "dZ: " + str(tag.pose_t[2]), (int(center[0]), int(center[1]) + 60), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 0), 2)

            rot = tag.pose_R
            #print(rot)
            
            rel_pos = (tag.pose_t[0], tag.pose_t[2])
            rel_rot = util.getRotation(rot)
            rot_read = (math.degrees(rel_rot[0]),
                        math.degrees(rel_rot[1]),
                        math.degrees(rel_rot[2]))
            # print(rel_pos)
            # abs_pos = util.getPosition(rel_pos, rel_rot, tag.tag_id)
            abs_pos = util.getPositionNew(tag.pose_t, tag.pose_R, tag.tag_id)
            # print(abs_pos)
            # print(tag.tag_id)
            # print(abs_pos)
            # cv2.putText(frame, "rot: " + str(rot), (int(center[0]), int(center[1]) + 90), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 0), 2)
            abs_pos_possible.append((abs_pos, tag.pose_err))


            # pos = util.getPosition((tag.pose_t[0], tag.pose_t[2]), tag.tag_id)
            # cv2.putText(frame, "Pos: " + str(pos), (int(center[0]), int(center[1]) + 90), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 0), 2)

        true_pos = None
        if len(abs_pos_possible) > 0:
            sum = 0
            dividend = 0

            for possible_pos in abs_pos_possible:
                dividend += possible_pos[1]
                sum += possible_pos[0] * possible_pos[1]

            true_pos = sum / dividend

        if true_pos is not None:
            if previous_pos is not None:
                previous_pos = util.lerp(previous_pos, true_pos, 0.6) # Turn this down to increase smoothing, must be between 0 and 1
            else:
                previous_pos = true_pos

            true_pos = previous_pos

            cv2.putText(frame, str([round(true_pos[0][0] * 100) / 100, round(true_pos[1][0] * 100) / 100, round(true_pos[2][0] * 100) / 100]), (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 0, 255), 2)

        cv2.imshow("frame", frame)

        key = cv2.waitKey(1) & 0xFF

        if key == ord("q"):
            break

        if current_frame == frame_buffer - 1:
            current_frame = 0
            validated.clear()
            for i in detected_tags.keys(): validated.append(int(i))
            detected_tags.clear()
        else:
            current_frame += 1

    cap.release()
    cv2.destroyAllWindows()

    x_distance_pub.close()
    z_distance_pub.close()


if __name__ == "__main__":
    main()

