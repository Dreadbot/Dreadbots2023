package frc.robot.commands.autonCommands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drive;

public class AutonDriveStraightCommand extends CommandBase {
    private final Drive drive;
    private final double distance;
    private double startPosition;
    private RelativeEncoder encoder;

    public AutonDriveStraightCommand(Drive drive, double distance) {
        this.drive = drive;
        this.distance = distance;
        this.encoder = drive.getMotorEncoder(1);
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        startPosition = encoder.getPosition();
    }

    @Override
    public void execute() {
        drive.ArcadeDrive(DriveConstants.AUTON_DRIVE_SPEED, 0, false, false, false);
    }

    @Override
    public boolean isFinished() {
        double metersTraveled = ((encoder.getPosition() - startPosition) / DriveConstants.GEAR_RATIO) * DriveConstants.WHEEL_CIRCUMFRENCE;
        //System.out.println("Meters Travelled: " + metersTraveled);

        return metersTraveled >= distance;
    }
}
