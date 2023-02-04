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
    protected double lastForward;
    protected double lastRotation;

    public DriveCommand(Drive drive, DoubleSupplier joystickForwardAxis, DoubleSupplier joystickRotationalAxis) {
        this.drive = drive;
        this.joystickForwardAxis = joystickForwardAxis;
        this.joystickRotationalAxis = joystickRotationalAxis;
        this.turboMode = false;
        this.turtleMode = false;
        addRequirements(drive);
    }

    /*  Normal Mode: move at 75% of joystick value (min speed = 0; max speed = .75)
     *  Turtle Mode: move at 40% of joystick value (min speed = 0; max speed = .40)
     *  Turbo Mode: move at 60% of joystick value plus 40% (min speed = 40%, max speed = 100%)
     */
    @Override
    public void execute() {
        double forward = -(joystickForwardAxis.getAsDouble() * DriveConstants.FORWARD_SPEED_LIMITER);
        double rotation = -(joystickRotationalAxis.getAsDouble() * DriveConstants.ROT_SPEED_LIMITER);

        if (this.turboMode) {
            forward = Math.signum(joystickForwardAxis.getAsDouble()) * DreadbotMath.linearInterpolation(0.4, 1, Math.abs(joystickForwardAxis.getAsDouble()));
            // Because this is done after the linearInterpolation, the deadband ends up being .05
            System.out.println(Math.abs(forward));
            if (Math.abs(forward) <= OperatorConstants.TURBO_CONTROLLER_DEADBAND) {
                forward = 0;
            }
        } else if (this.turtleMode) {
            forward = Math.signum(joystickForwardAxis.getAsDouble()) * DreadbotMath.linearInterpolation(0, 0.4, Math.abs(joystickForwardAxis.getAsDouble()));
            rotation =  Math.signum(joystickRotationalAxis.getAsDouble()) * DreadbotMath.linearInterpolation(0, 0.4,  Math.abs(joystickRotationalAxis.getAsDouble()));
        }
        drive.ArcadeDrive(forward, rotation);
        // save off the values so they are available for unit tests
        lastForward = forward;
        lastRotation = rotation;
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
