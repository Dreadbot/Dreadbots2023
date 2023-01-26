package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drive;
import util.math.DreadbotMath;

public class DriveCommand extends CommandBase {
    private final Drive drive;
    private final DoubleSupplier joystickForwardAxis;
    private final DoubleSupplier joystickRotationalAxis;
    private boolean turboMode;
    private boolean turtleMode;
    public DriveCommand(Drive drive, DoubleSupplier joystickForwardAxis, DoubleSupplier joystickRotationalAxis) {
        this.drive = drive;
        this.joystickForwardAxis = joystickForwardAxis;
        this.joystickRotationalAxis = joystickRotationalAxis;
        this.turboMode = false;
        this.turtleMode = false;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        double joystickForward = (joystickForwardAxis.getAsDouble() * DriveConstants.FORWARD_SPEED_LIMITER);
        double joystickRotation = (joystickRotationalAxis.getAsDouble() * DriveConstants.ROT_SPEED_LIMITER);

        if(this.turboMode) {
            joystickForward = DreadbotMath.linearInterpolation(0.4, 1, joystickForwardAxis.getAsDouble());
            if(joystickForward <= OperatorConstants.TURBO_CONTROLLER_DEADBAND) {
                joystickForward = 0;
            }
        } else if(this.turtleMode) {
            joystickForward = DreadbotMath.linearInterpolation(0, 0.4, joystickForwardAxis.getAsDouble());
            joystickRotation = DreadbotMath.linearInterpolation(0, 0.4, joystickRotationalAxis.getAsDouble());
        }
        drive.ArcadeDrive(joystickForward, joystickRotation);
    }
    public void enableTurbo() {
        this.turboMode = true;
    }
    public void disableTurbo() {
        this.turboMode = false;
    }
    public void enableTurtle() {
        this.turtleMode = true;
    }
    public void disableTurtle() {
        this.turtleMode = false;
    }
}
