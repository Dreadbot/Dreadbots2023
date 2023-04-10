package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class FieldOrientationCommand extends CommandBase {
    DriveCommand driveCommand;

    public FieldOrientationCommand(DriveCommand driveCommand) {
        this.driveCommand = driveCommand;
    }

    @Override
    public void execute() {
        driveCommand.toggleFieldOrientation();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
