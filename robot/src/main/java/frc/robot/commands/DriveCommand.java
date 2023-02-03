package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveCommand extends CommandBase {
    private final Drive drive;
    private final DoubleSupplier xAxis;
    private final DoubleSupplier rotAxis;
    public DriveCommand(Drive drive, DoubleSupplier xAxis, DoubleSupplier rotAxis) {
        this.drive = drive;
        this.xAxis = xAxis;
        this.rotAxis = rotAxis;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        double xSpeed = xAxis.getAsDouble() * 0.5;
        double rotSpeed = rotAxis.getAsDouble() * 0.5;
        drive.arcadeDrive(xSpeed, rotSpeed);
    }
}
