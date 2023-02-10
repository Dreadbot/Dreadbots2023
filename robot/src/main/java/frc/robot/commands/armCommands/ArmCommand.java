package frc.robot.commands.armCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.Arm;

public class ArmCommand extends CommandBase {
    private Arm arm;
    private DoubleSupplier joystickValue;
    public ArmCommand(Arm arm, DoubleSupplier joystickValue) {
        this.arm = arm;
        this.joystickValue = joystickValue;
        addRequirements(arm);
    }
    @Override
    public void execute() {
        arm.elevate(joystickValue.getAsDouble() * ArmConstants.ELEVATOR_SPEED_LIMITER);
    }
}
