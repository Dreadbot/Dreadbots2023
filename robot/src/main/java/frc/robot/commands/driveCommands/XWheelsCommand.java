package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class XWheelsCommand extends CommandBase {
    Drive drive;


    public XWheelsCommand(Drive drive) {
        this.drive = drive;
    }

    @Override
    public void execute() {
        drive.xModules();
    }

    @Override 
    public void end(boolean interrupted){
        drive.isXMode = false;
    }
}
