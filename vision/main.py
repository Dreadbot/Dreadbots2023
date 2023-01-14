import cv2
from dt_apriltags import Detector

cap = cv2.VideoCapture(0)
_, frame = cap.read()
image = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
cap.release()

# imagepath = 'frame.png'
# image = cv2.imread(imagepath, cv2.IMREAD_GRAYSCALE)

at_detector = Detector(searchpath=['apriltags'],
        families='tag16h5',
        nthreads=2,
        quad_sigma=0.2,
        decode_sharpening=0.25,
    )

# with camera LC3
camera_params = [660.1986, 660.2282, 314.2296, 235.1862]; # fx, fy, cx, cy

tags = at_detector.detect(image, estimate_tag_pose=True, camera_params=camera_params, tag_size=0.1397)
print(tags)

