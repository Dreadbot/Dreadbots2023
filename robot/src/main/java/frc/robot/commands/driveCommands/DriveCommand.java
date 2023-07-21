package frc.robot.commands.driveCommands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drive;
import util.math.DreadbotMath;
import util.math.Vector2D;

public class DriveCommand extends CommandBase {
    private final Drive drive;
    private final DoubleSupplier joystickForwardAxis;
    private final DoubleSupplier joystickStrafeAxis;
    private final DoubleSupplier joystickRotationalAxis;
    private boolean turboMode = false;
    private boolean turtleMode = false;
    private boolean fieldOriented = true;
    protected double lastForward;
    protected double lastRotation;

    public DriveCommand(Drive drive, DoubleSupplier joystickForwardAxis, DoubleSupplier joystickStrafeAxis, DoubleSupplier joystickRotationalAxis) {
        this.drive = drive;
        this.joystickForwardAxis = joystickForwardAxis;
        this.joystickStrafeAxis = joystickStrafeAxis;
        this.joystickRotationalAxis = joystickRotationalAxis;
        addRequirements(drive);
        SmartDashboard.putBoolean("Field Oriented", fieldOriented);
    }

    /*  Normal Mode: move at 75% of joystick value (min speed = 0; max speed = .75)
     *  Turtle Mode: move at 40% of joystick value (min speed = 0; max speed = .40)
     *  Turbo Mode: move at 60% of joystick value plus 40% (min speed = 40%, max speed = 100%)
     */
    @Override
    public void execute() {
        Vector2D joystickValue = DreadbotMath.applyDeadbandToVector(new Vector2D(joystickForwardAxis.getAsDouble(), joystickStrafeAxis.getAsDouble()), 0.10);

        double forward = joystickValue.x1 * DriveConstants.FORWARD_SPEED_LIMITER;
        double strafe = joystickValue.x2 * DriveConstants.STRAFE_SPEED_LIMITER;
        double rotation = (DreadbotMath.applyDeadbandToValue(joystickRotationalAxis.getAsDouble(), DriveConstants.DEADBAND) * DriveConstants.ROT_SPEED_LIMITER);

        boolean addSlew = true;
        if (this.turboMode) {
            // make forward negative right here and test
            forward = Math.signum(joystickForwardAxis.getAsDouble()) * DreadbotMath.linearInterpolation(DriveConstants.TURBO_MODE_MIN_SPEED, 1, Math.abs(joystickForwardAxis.getAsDouble()));
            strafe = Math.signum(joystickStrafeAxis.getAsDouble()) * DreadbotMath.linearInterpolation(DriveConstants.TURBO_MODE_MIN_SPEED, 1, Math.abs(joystickStrafeAxis.getAsDouble()));
            // Because this is done after the linearInterpolation, the deadband ends up being .05
            if (Math.abs(forward) <= OperatorConstants.TURBO_CONTROLLER_DEADBAND) {
                forward = 0;
            }
        } else if (this.turtleMode) {
            // make forward negative right here and test
            forward = Math.signum(joystickForwardAxis.getAsDouble()) * DreadbotMath.linearInterpolation(0, DriveConstants.TURTLE_MODE_MAX_SPEED, Math.abs(joystickForwardAxis.getAsDouble()));
            strafe = Math.signum(joystickStrafeAxis.getAsDouble()) * DreadbotMath.linearInterpolation(0, DriveConstants.TURTLE_MODE_MAX_SPEED, Math.abs(joystickStrafeAxis.getAsDouble()));
            rotation =  Math.signum(joystickRotationalAxis.getAsDouble()) * DreadbotMath.linearInterpolation(0, DriveConstants.TURTLE_MODE_MAX_SPEED,  Math.abs(joystickRotationalAxis.getAsDouble()));
            addSlew = false;
        }

        // --- Driver assist code; may help with drift ---
        // if (forward > 0 && !fieldOriented) {
        //     rotation -= 0.06 * forward;
        // } else if (forward < 0 && !fieldOriented) {
        //     rotation -= 0.03 * forward;
        // }

        drive.drive(forward, strafe, rotation, fieldOriented);// addSlew, turboMode); //invert forward and rotation axis
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

    public void toggleFieldOrientation() {
        this.fieldOriented = !this.fieldOriented;
        SmartDashboard.putBoolean("Field Oriented", fieldOriented);
    }
}