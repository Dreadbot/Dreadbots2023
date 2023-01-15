import cv2
from dt_apriltags import Detector
import yaml
import argparse
import os

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
    frame_buffer = 3
    detected_tags = {}
    validated = []

    def valid_tag(id):
        return id in validated and (detected_tags[id] and False not in detected_tags)


    def result_callback(tag, frame):
        tag_id = tag.tag_id
        if tag_id not in detected_tags.keys():
            detected_tags[tag_id] = [False] * frame_buffer

        detected_tags[tag_id][current_frame] = True

        if not valid_tag(tag_id): return

        for c in range(4):
            toI = c + 1
            if(c == 3): toI = 0

            original = tag.corners[c]
            to = tag.corners[toI]
            
            cv2.line(frame, (int(original[0]), int(original[1])), (int(to[0]), int(to[1])), (0, 255, 0), 2)

        center = tag.center
        centerCord = (int(center[0]), int(center[1]))
        cv2.putText(frame, "dZ: " + str(tag.pose_t[2]), centerCord, cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 0), 2)
    

    while True:
        _, frame = cap.read()
        image = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        tags = at_detector.detect(image, estimate_tag_pose=True, camera_params=camera_params, tag_size=0.1397)
        for tag in tags:
            result_callback(tag, frame)


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

