package frc.robot.commands.armCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Grabber;

public class ArmCommand extends CommandBase {
    private Arm arm;
    private Grabber grabber;
    private DoubleSupplier joystickValue;
    public ArmCommand(Arm arm, Grabber grabber,  DoubleSupplier joystickValue) {
        this.arm = arm;
        this.grabber = grabber;
        this.joystickValue = joystickValue;
        addRequirements(arm);
    }
    int i = 0;
    @Override
    public void execute() {
        if(arm.getLowerSwitch() && joystickValue.getAsDouble() < 0.05){
            grabber.openGrabber();
        } else if (Math.abs(joystickValue.getAsDouble()) > 0.05 && arm.getElevatorPosition() < ArmConstants.LOW_POST_POSITION - 10) {
            grabber.closeGrabber(); 
        }
        arm.elevate(joystickValue.getAsDouble() * ArmConstants.ELEVATOR_MANUAL_SPEED);
    }
}
