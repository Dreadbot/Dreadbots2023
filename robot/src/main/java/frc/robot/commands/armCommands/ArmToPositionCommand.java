package frc.robot.commands.armCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    public void initialize(){
        direction = Math.signum(rotations - arm.getElevatorPosition());
    }

    @Override
    public void execute() {
        double armSpeed = 1;
        if(arm.getElevatorPosition() < ArmConstants.PICKUP_ELEVATOR_POSITION - 3 || arm.getElevatorPosition() > ArmConstants.MAX_ELEVATOR_POSITION - 10) {
            armSpeed = 0.6; // slow down the bot if we are close to 0
        }
        if(arm.getLowerSwitch() && direction < 0) {
            grabber.openGrabber();
        } else if(direction == -1 && arm.getElevatorPosition() < ArmConstants.LOW_POST_POSITION - 10) {
            grabber.closeGrabber();
        } else if(direction == 1) {
            grabber.closeGrabber();
        }
        arm.elevate(direction * armSpeed * ArmConstants.ELEVATOR_MOTOR_SPEED);
    }
    @Override
    public boolean isFinished() {
        if (Math.abs(cancelJoystick.getAsDouble()) > .05 ) {
            return true;
        }
        if(direction > 0) {
            return arm.getElevatorPosition() > rotations || Math.abs(arm.getElevatorPosition() - rotations) < 0.2 || arm.getUpperSwitch();
        } else {
            return arm.getElevatorPosition() < rotations || Math.abs(arm.getElevatorPosition() - rotations) < 0.2 || arm.getLowerSwitch();
        }
    }
    @Override
    public void end(boolean interrupted){
        arm.stopMotors();
    }
}
