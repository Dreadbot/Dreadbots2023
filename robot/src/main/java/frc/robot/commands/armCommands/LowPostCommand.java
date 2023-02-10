package frc.robot.commands.armCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class LowPostCommand extends CommandBase{
    private Arm arm;
    public LowPostCommand(Arm arm) {
        this.arm = arm;
    }
    @Override
    public void execute() {
        System.err.println(this.getClass().getName() + " is not implemented!");
    }
}
