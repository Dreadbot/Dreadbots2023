import json

def getPosition(dist_tuple: (int, int), tag_id: int):
    """
    Returns the current position of the camera on the game field

    :param (int, int) dist_tuple: The distance from the camera to the tag (X, Z)
    :param int tag_id: The ID of the detected tag
    :return: (X, Z)
    :rtype: (int, int)
    """

    dist_from_tag = (-1 * dist_tuple[0], -1 * dist_tuple[1])

    f = open('apriltag_positions.json')
    tag_coord_object = json.load(f)["points"][str(tag_id)]
    tag_coord = (int(tag_coord_object["x"]), int(tag_coord_object["z"]))
    return (tag_coord[0] + dist_from_tag[0], tag_coord[1] + dist_from_tag[1])

