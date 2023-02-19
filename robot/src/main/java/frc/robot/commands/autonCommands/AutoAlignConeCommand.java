package frc.robot.commands.autonCommands;

import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.Constants.AutoAlignConstants;
import frc.robot.Constants.NetworkTableConstants;

public class AutoAlignConeCommand extends CommandBase {
    private Drive drive;
    private DoubleTopic angleToNodeTopic;
    private DoubleSubscriber angleToNode;
    private DoubleTopic distanceToNodeTopic;
    private DoubleSubscriber distanceToNode;
    private PowerDistribution pdh;

    public AutoAlignConeCommand(Drive drive, PowerDistribution pdh, NetworkTable table) {
        this.drive = drive;
        this.angleToNodeTopic = table.getDoubleTopic(NetworkTableConstants.ANGLE_TO_CONE_NODE);
        this.angleToNode = this.angleToNodeTopic.subscribe(0.0);
        this.distanceToNodeTopic = table.getDoubleTopic(NetworkTableConstants.DISTANCE_TO_CONE_NODE);
        this.distanceToNode = this.distanceToNodeTopic.subscribe(0.0);
        this.pdh = pdh;
    }

    @Override
    public void initialize() {
        pdh.setSwitchableChannel(true);
    }

    @Override
    public void execute() {
        drive.ArcadeDrive(0, AutoAlignConstants.CONE_ROTATION_SPEED * Math.signum(angleToNode.get()));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(angleToNode.get()) <= AutoAlignConstants.CONE_ANGLE_MARGIN_OF_ERROR;
    }
}
