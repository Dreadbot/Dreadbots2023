package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drive;

public class FeedMotorsCommand extends InstantCommand {
    private Drive drive;
    
    public FeedMotorsCommand(Drive drive) {
        this.drive = drive;
    }

    @Override
    public void execute() {
        drive.feedMotors();
    }
}
