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
    private Grabber grabber;
    private DoubleSupplier cancelJoystick;
    public ArmToPositionCommand(Arm arm, Grabber grabber, double rotations, DoubleSupplier cancelJoystick) {
        this.arm = arm;
        this.rotations = rotations;
        this.grabber = grabber;
        this.cancelJoystick = cancelJoystick;
    }
    @Override
    public void execute() {
        double direction = Math.signum(rotations - arm.getElevatorPosition());
        if(arm.getLowerSwitch()){
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
        return DreadbotMath.inRange(arm.getElevatorPosition(), rotations - 5, rotations + 5) || Math.abs(cancelJoystick.getAsDouble()) > 0.05;
    }
}
