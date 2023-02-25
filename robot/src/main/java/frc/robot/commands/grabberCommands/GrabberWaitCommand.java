package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Grabber;

public class GrabberWaitCommand extends WaitCommand {
    public GrabberWaitCommand(double seconds, Grabber grabber) {
        super(seconds);
        //TODO Auto-generated constructor stub
        addRequirements(grabber);
    }
    
}
