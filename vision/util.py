import json

# dist
def getPosition(dist_tuple: (int, int), tag_id):
    """
    Returns the current position of the camera on the game field

    :param (int, int) dist_tuple: The distance from the camera to the tag (X, Z)
    :param int tag_id: The ID of the detected tag
    :return: (X, Z)
    :rtype: (int, int)
    """

    dist_from_tag = (-dist_tuple[0], -dist_tuple[1])

    # TODO: add correct coordinate file
    f = open('data.json')
    coords = json.load(f)
    tag_coord = coords
    return ((dis))

    # returns JSON object as 
    # a dictionary
    
    
    

    print("amongus")

