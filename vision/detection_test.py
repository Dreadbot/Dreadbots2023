import cv2
from dt_apriltags import Detector
import yaml
import argparse
import os
import util
import math

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("filename")
    args = parser.parse_args()

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
    cap.set(cv2.CAP_PROP_EXPOSURE, -4) 

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

        
    while True:
        _, frame = cap.read()

        image = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        tags = at_detector.detect(image, estimate_tag_pose=True, camera_params=camera_params, tag_size=0.1397)
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
            rot_read = [0] * 3
            for i in range(3):
                rot_read[i] = math.degrees(rel_rot[i])
            # print(rot_read)
            abs_pos = util.getPosition(rel_pos, rel_rot, tag.tag_id)
            print(abs_pos)
            # cv2.putText(frame, "rot: " + str(rot), (int(center[0]), int(center[1]) + 90), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 0), 2)


#            pos = util.getPosition((tag.pose_t[0], tag.pose_t[2]), tag.tag_id)
#            cv2.putText(frame, "Pos: " + str(pos), (int(center[0]), int(center[1]) + 90), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 0), 2)
            

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


if __name__ == "__main__":
    main()

