package frc.robot.commands.armCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.Arm;

public class ExtendArmCommand extends CommandBase {
    private Arm arm;

    public ExtendArmCommand(Arm arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        arm.elevate(ArmConstants.ELEVATOR_MOTOR_SPEED);
        //System.out.println(arm.getElevatorPosition());
    }
    @Override
    public boolean isFinished() {
        return arm.getUpperSwitch();
    }

    @Override
    public void end(boolean interrupted) {
        arm.stopMotors();
    }
}
