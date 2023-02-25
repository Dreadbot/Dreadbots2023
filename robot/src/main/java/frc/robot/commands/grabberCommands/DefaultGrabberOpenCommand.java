package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Arm;

public class DefaultGrabberOpenCommand extends CommandBase {
    private Grabber grabber;
    private Arm arm;
    public DefaultGrabberOpenCommand(Grabber grabber, Arm arm){
        this.grabber = grabber;
        this.arm = arm;
        addRequirements(grabber);
    }

    @Override
    public void execute(){
        if(arm.getLowerSwitch())
            grabber.openGrabber();
    }
}
