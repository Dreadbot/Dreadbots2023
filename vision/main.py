import cv2
from dt_apriltags import Detector

cap = cv2.VideoCapture(0)
at_detector = Detector(searchpath=['apriltags'],
            families='tag16h5',
            nthreads=2,
            quad_sigma=0.2,
            decode_sharpening=0.25,
        )

while True:
    _, frame = cap.read()
    image = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # imagepath = 'frame.png'
    # image = cv2.imread(imagepath, cv2.IMREAD_GRAYSCALE)

    # with camera LC3
    camera_params = [660.1986, 660.2282, 314.2296, 235.1862]; # fx, fy, cx, cy

    tags = at_detector.detect(image, estimate_tag_pose=True, camera_params=camera_params, tag_size=0.1397)
    for tag in tags:
        for c in range(4):
            toI = c + 1
            if(c == 3): toI = 0

            original = tag.corners[c]
            to = tag.corners[toI]
            
            cv2.line(frame, (int(original[0]), int(original[1])), (int(to[0]), int(to[1])), (0, 255, 0), 2)

        center = tag.center
        centerCord = (int(center[0]), int(center[1]))
        cv2.putText(frame, "dZ: " + str(tag.pose_t[2]), centerCord, cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 0), 2)


    # print(tags)
    cv2.imshow("frame", frame)

    key = cv2.waitKey(1) & 0xFF

    if key == ord("q"):
        break

cv2.destroyAllWindows()



