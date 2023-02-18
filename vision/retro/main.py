import cv2
import numpy as np
import math


camera = cv2.VideoCapture(0)
camera.set(cv2.CAP_PROP_AUTO_EXPOSURE, 1)
camera.set(cv2.CAP_PROP_EXPOSURE, 5)
# windowName = "green"
# sliderName = "slidin"
# sizeThreshold = 1
# hueMin = 1
# saturationMin = 1
# valueMin = 1
# hueMax = 360
# saturationMax = 255
# valueMax = 255
#
# def hueMinOnChange(val):
#     global hueMin
#     hueMin = val
#     return hueMin
# def saturationMinOnChange(val):
#     global saturationMin
#     saturationMin = val
#     return saturationMin
# def valueMinOnChange(val):
#     global valueMin
#     valueMin = val
#     return valueMin
# def hueMaxOnChange(val):
#     global hueMax
#     hueMax = val
#     return hueMax
# def saturationMaxOnChange(val):
#     global saturationMax
#     saturationMax = val
#     return saturationMax
# def valueMaxOnChange(val):
#     global valueMax
#     valueMax = val
#     return valueMax
#
# cv2.namedWindow(sliderName)
# cv2.createTrackbar("hueMin", sliderName, 0, 359, hueMinOnChange)
# cv2.createTrackbar("hueMax", sliderName, 0, 359, hueMaxOnChange)
# cv2.createTrackbar("saturationMin", sliderName, 0, 255, saturationMinOnChange)
# cv2.createTrackbar("saturationMax", sliderName, 0, 255, saturationMaxOnChange)
# cv2.createTrackbar("valueMin", sliderName, 0, 255, valueMinOnChange)
# cv2.createTrackbar("valueMax", sliderName, 0, 255, valueMaxOnChange)

hueMin = 20
hueMax = 40
saturationMin = 20
saturationMax = 50
valueMin = 230
valueMax = 255

while True:

    ret, frame = camera.read()
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    hsvFrame = cv2.inRange(hsv, (hueMin, saturationMin, valueMin), (hueMax, saturationMax, valueMax))
    frameCopy = frame.copy()
    contours, hierarchy = cv2.findContours(hsvFrame, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    if len(contours) == 0:
        pass
    else:
        cnt = contours[0]
        area = cv2.contourArea(cnt)
        if area > sizeThreshold:
            x, y, w, h = cv2.boundingRect(cnt)
            cv2.drawContours(frameCopy, cnt, -1, (0,0,255), 1, cv2.LINE_AA)
            cv2.rectangle(frameCopy, (x, y), ((x+w), (y+h)), (255,0,0))
            hPixel = h
            xPixel = (x + (w / 2))
            yPixel = (y + (h / 2))
            xTriangle = xPixel - 360
            yTriangle = yPixel - 240
            if xTriangle-360 < 0 or xTriangle - 360 > 0:
                # print(np.degrees(np.emath.arctanh(xTriangle / 667)))
                continue
            else:
                print("angle is 0")
            if hPixel > 0:
                distance = 667 * (4 / hPixel)
                print("the distance is " + str(distance) + "inches")
                
    cv2.imshow(windowName, hsvFrame)

    if cv2.waitKey(1) & 0xFF == ord("q"):
        break

# cv2.destroyAllWindows()
