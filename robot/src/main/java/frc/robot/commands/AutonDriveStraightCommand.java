package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drive;

public class AutonDriveStraightCommand extends CommandBase {
    private final Drive drive;
    private final double distance;
    private double startPosition;

    public AutonDriveStraightCommand(Drive drive, double distance) {
        this.drive = drive;
        this.distance = distance;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        startPosition = drive.getMotorEncoder(1).getPosition();
    }

    @Override
    public void execute() {
        drive.ArcadeDrive(.25, 0);
        System.out.println(((drive.getMotorEncoder(1).getPosition() - startPosition) / DriveConstants.GEAR_RATIO) * DriveConstants.WHEEL_CIRCUMFRENCE);
    }

    @Override
    public boolean isFinished() {
        double metersTraveled = ((drive.getMotorEncoder(1).getPosition() - startPosition) / DriveConstants.GEAR_RATIO) * DriveConstants.WHEEL_CIRCUMFRENCE;

        return metersTraveled >= distance;
    }
}
