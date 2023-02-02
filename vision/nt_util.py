import ntcore

class DoublePublisher:
    """
    A wrapper for publishing to a NetworkTable instance.
    Publishes values to a topic of type double.
    """

    def __init__(self, table: ntcore.NetworkTable, topic_name: string):
        self.topic = table.getDoubleTopic(topic_name)
        self.pub = self.topic.publish()
        self.pub.setDefault(0.0)

    def publish(self, value: number):
        """
        Publish a value.

        :param value: Value to publish
        """
        self.pub.set(value)

    def close(self):
        """
        Close the publisher, removing the connection to the topic.
        """
        self.pub.close()
