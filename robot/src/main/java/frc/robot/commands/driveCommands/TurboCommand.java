package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurboCommand extends CommandBase{
    private DriveCommand driveCommand;

    public TurboCommand(DriveCommand driveCommand) {
        this.driveCommand = driveCommand;
    }

    @Override
    public void execute() {
        driveCommand.enableTurbo();
    }

    @Override
    public void end(boolean interrupted) {
        driveCommand.disableTurbo();
    }
}
