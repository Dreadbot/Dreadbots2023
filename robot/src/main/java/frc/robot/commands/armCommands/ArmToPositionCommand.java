package frc.robot.commands.armCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Grabber;
import util.math.DreadbotMath;

public class ArmToPositionCommand extends CommandBase{
    private Arm arm;
    private double rotations;
    private double direction;
    private Grabber grabber;
    private DoubleSupplier cancelJoystick;
    public ArmToPositionCommand(Arm arm, Grabber grabber, double rotations, DoubleSupplier cancelJoystick) {
        this.arm = arm;
        this.rotations = rotations;
        this.grabber = grabber;
        this.cancelJoystick = cancelJoystick;
        addRequirements(arm, grabber);
    }
    @Override
    public void execute() {
        direction = Math.signum(rotations - arm.getElevatorPosition());
        if(arm.getLowerSwitch() && direction < 0) {
            grabber.openGrabber();
        } else if(direction == -1 && arm.getElevatorPosition() < ArmConstants.LOW_POST_POSITION - 10) {
            grabber.closeGrabber();
        } else if(direction == 1) {
            grabber.closeGrabber();
        }
        arm.elevate(direction * ArmConstants.ELEVATOR_MOTOR_SPEED);
    }
    @Override
    public boolean isFinished() {
        if (Math.abs(cancelJoystick.getAsDouble()) > .05 ) {
            return true;
        }
        if(direction > 0) {
            return arm.getElevatorPosition() > rotations || arm.getUpperSwitch();
        } else {
            return arm.getElevatorPosition() < rotations || arm.getLowerSwitch();
        }
    }
}
