package frc.robot.commands.virtualCommands;

import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.IntegerTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CameraCommand extends CommandBase {
    private final IntegerTopic selectionTopic;
    private final IntegerPublisher selectionPub;
    private boolean intakeCamActive = true;

    public CameraCommand(NetworkTable table) {
        this.selectionTopic = table.getIntegerTopic("CameraSelection");
        this.selectionPub = this.selectionTopic.publish();
        this.selectionPub.set(intakeCamActive ? 1 : 0);
    }

    @Override
    public void execute() {
        intakeCamActive = !intakeCamActive;
        this.selectionPub.set(intakeCamActive ? 1 : 0);
    }
}
