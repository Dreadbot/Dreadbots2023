package frc.robot.commands.autonCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class AutoAlignCubeCommand extends CommandBase {
    private Drive drive;
    public AutoAlignCubeCommand(Drive drive) {
        this.drive = drive;
        //TODO: implement this class
    }
    @Override
    public void execute() {
        System.err.println(this.getClass().getName() + " is not implemented!");
    }
}
