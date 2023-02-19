package frc.robot.commands.autonCommands;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AutoAlignConstants;
import frc.robot.Constants.NetworkTableConstants;
import frc.robot.subsystems.Drive;

public class AutoAlignCubeCommand extends CommandBase {
    private Drive drive;
    private DoubleTopic distanceToTagXTopic;
    private DoubleSubscriber distanceToTagX;
    private DoubleTopic distanceToTagZTopic;
    private DoubleSubscriber distanceToTagZ;
    private PowerDistribution pdh;

    public AutoAlignCubeCommand(Drive drive, PowerDistribution pdh, NetworkTable table) {
        this.drive = drive;
        this.distanceToTagXTopic = table.getDoubleTopic(NetworkTableConstants.APRIL_TAG_X_DISTANCE);
        this.distanceToTagX = this.distanceToTagXTopic.subscribe(0.0);
        this.distanceToTagZTopic = table.getDoubleTopic(NetworkTableConstants.APRIL_TAG_Z_DISTANCE);
        this.distanceToTagZ = this.distanceToTagZTopic.subscribe(0.0);
        this.pdh = pdh;
    }

    @Override
    public void initialize() {
        pdh.setSwitchableChannel(false);
    }

    @Override
    public void execute() {
        drive.ArcadeDrive(0, AutoAlignConstants.CUBE_ROTATION_SPEED * Math.signum(distanceToTagX.get()));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(distanceToTagX.get()) <= AutoAlignConstants.CUBE_X_DISTANCE_MARGIN_OF_ERROR;
    }
}
