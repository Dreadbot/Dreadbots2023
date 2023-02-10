package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurtleCommand extends CommandBase{
    private DriveCommand driveCommand;
    public TurtleCommand(DriveCommand driveCommand) {
        this.driveCommand = driveCommand;
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        driveCommand.enableTurtle();
    }

    @Override
    public void end(boolean interrupted) {
        driveCommand.disableTurtle();
    }
}
