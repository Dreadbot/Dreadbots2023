import json
import math

def getYawRotation(rotation_matrix):
    """
    Returns the yaw rotation (around the camera Y-axis) of a given combined rotation matrix
    Combined 3d rotation matrix:
        Y: Yaw Rotation (In Camera Space)
        X: Pitch Rotation
        Z: Roll Rotation
        [
            [cos(Y)cos(X), cos(Y)sin(X)sin(Z)-sin(Y)cos(Z), cos(Y)sin(X)cos(Z)+sin(Y)sin(Z)],
            [sin(Y)cos(x), sin(Y)sin(X)sin(Z)+cos(Y)cos(Z), sin(Y)sin(X)cos(Z)-cos(Y)sin(Z)],
            [-sin(X), cos(X)sin(Z), cos(X)cos(Z)]
        ]

    :param rotation_matrix: Rotation matrix
    :return: Rotation in radians
    """
    pitch = -1 * math.asin(rotation_matrix[2][0])
    return math.acos(rotation_matrix[0][0] / math.cos(pitch))


def getPosition(dist_tuple: tuple[int, int], rot_yaw: int, tag_id: int):
    """
    Returns the current position of the camera on the game field

    :param (int, int) dist_tuple: The relative distance from the camera to the tag (X, Z)
    :param int rot_yaw: The rotation of the april tag from its flat plane (in radians)
    :param int tag_id: The ID of the detected tag
    :return: (X, Z)
    :rtype: (int, int)
    """
    
    dist_hyp_rel = math.sqrt((dist_tuple[0]**2) + (dist_tuple[1]**2))
    dist_x_abs = dist_hyp_rel * math.cos(rot_yaw)
    dist_z_abs = dist_hyp_rel * math.sin(rot_yaw)

    if tag_id <= 4 and tag_id >= 1:
        dist_x_abs *= -1
        dist_z_abs *= -1
    
    f = open('apriltag_positions.json')
    tag_coord_object = json.load(f)["points"][str(tag_id)]
    tag_coord = (int(tag_coord_object["x"]), int(tag_coord_object["z"]))
    return (tag_coord[0] + dist_x_abs, tag_coord[1] + dist_z_abs)

