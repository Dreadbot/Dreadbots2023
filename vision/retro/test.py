import cv2
from cv2 import drawContours
import numpy as np

cam = cv2.VideoCapture(1)

cam.set(cv2.CAP_PROP_AUTO_EXPOSURE, 0.25)

cam.set(cv2.CAP_PROP_EXPOSURE, -15)



while True:
    ret, img = cam.read()
    
    h = [20,50]

    s = [75, 125]

    l = [0,20]

    imgRGB = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

    blurImg = cv2.GaussianBlur(img, (7, 7), 0)

    binImg = cv2.inRange(blurImg,(h[0], s[0], l[0]), (h[1], s[1], l[1]) )

    kernel = np.ones((8,8), np.uint8)

    eroImg = cv2.erode(binImg, kernel)

    dilImg = cv2.dilate(eroImg,(7,7), 20)

    contours, hierarchy = cv2.findContours(dilImg, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)

    conImg = drawContours(img, contours, -1, (0,0, 255), 2, cv2.LINE_AA)



    for c in contours:
        bounds = cv2.boundingRect

        x, y, w, h = bounds

        img_h, img_w = img.shape[0], img.shape[1]

        


    cv2.imshow('WINDOW', img)

    

    if cv2.waitKey(1) & 0xFF == ord('f'):
        break