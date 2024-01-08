#!/usr/bin/env python3
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
import typing

class ApriltagCamera:
    def __init__(camera_id: int, calibration_file: typing.Union[str, bytes, os.PathLike]):
        self.intrinsics = [0] * 4
        params = yaml.safe_load(calibration_file)["pinhole"]
        self.intrinsics[0] = params["fx"]
        self.intrinsics[1] = params["fy"]
        self.intrinsics[2] = params["cx"]
        self.intrinsics[3] = params["cy"]

        self.cap = cv2.VideoCapture(camera_id)

        self.detector = Detector(searchpath=['apriltags'],
                                 families='tag16h5',
                                 nthreads=2,
                                 quad_sigma=0.2,
                                 decode_sharpening=0.25)
        self.current_frame = 0
        self.frame_buffer = 5
        self.detected_tags = {}
        self.validated = []
        self.previous_frame = None

    def valid_tag(self, id):
        return (self.detected_tags[id] and False not in detected_tags[id])

    def result_callback(self, tag, frame):
        tag_id = tag.tag_id
        if tag_id not in self.detected_tags.keys():
            self.detected_tags[tag_id] = [False] * self.frame_buffer

        detected_tags[tag_id][self.current_frame] = True

        if tag_id not in self.validated and not self.valid_tag(tag_id): return

        for c in range(4):
            toI = c + 1
            if (c == 3): toI = 0

            original = tag.corners[c]
            to = tag.corners[toI]

            cv2.line(frame, (int(original[0]), int(original[1])), (int(to[0]), int(to[1])), (0, 255, 0), 2)

    def capture():
        pass
