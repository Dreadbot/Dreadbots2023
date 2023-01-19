package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drive;
import util.math.DreadbotMath;

public class DriveCommand extends CommandBase {
    private final Drive drive;
    private final DoubleSupplier joystickForwardAxis;
    private final DoubleSupplier joystickRotationalAxis;
    private boolean turboMode;
    public DriveCommand(Drive drive, DoubleSupplier joystickForwardAxis, DoubleSupplier joystickRotationalAxis) {
        this.drive = drive;
        this.joystickForwardAxis = joystickForwardAxis;
        this.joystickRotationalAxis = joystickRotationalAxis;
        this.turboMode = false;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        double joystickForward = (joystickForwardAxis.getAsDouble() * DriveConstants.FORWARD_SPEED_LIMITER);
        double joystickRotation = (joystickRotationalAxis.getAsDouble() * DriveConstants.ROT_SPEED_LIMITER);

        if(this.turboMode) {
            joystickForward = DreadbotMath.linearInterpolation(0.4f, 1f, joystickForwardAxis.getAsDouble());
            if(joystickForward <= 0.43f) {
                joystickForward = 0;
            }
        }
        drive.ArcadeDrive(joystickRotation, joystickForward);
    }
    public void enableTurbo() {
        this.turboMode = true;
    }
    public void disableTurbo() {
        this.turboMode = false;
    }
}
