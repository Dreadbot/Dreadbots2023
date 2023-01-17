import json
import math

def getPosition(dist_tuple: tuple[int, int], rot_yaw: int, tag_id: int):
    """
    Returns the current position of the camera on the game field

    :param (int, int) dist_tuple: The relative distance from the camera to the tag (X, Z)
    :param int rot_yaw: The rotation of the april tag from its flat plane
    :param int tag_id: The ID of the detected tag
    :return: (X, Z)
    :rtype: (int, int)
    """
    
    dist_hyp_rel = math.sqrt((dist_tuple[0]**2) + (dist_tuple[1]**2))
    dist_x_abs = dist_hyp_rel * math.cos(rot_yaw)
    dist_z_abs = dist_hyp_rel * math.sin(rot_yaw)

    dist_from_tag = (-1 * dist_tuple[0], -1 * dist_tuple[1])
    
    f = open('apriltag_positions.json')
    tag_coord_object = json.load(f)["points"][str(tag_id)]
    tag_coord = (int(tag_coord_object["x"]), int(tag_coord_object["z"]))
    return (tag_coord[0] + dist_from_tag[0], tag_coord[1] + dist_from_tag[1])

