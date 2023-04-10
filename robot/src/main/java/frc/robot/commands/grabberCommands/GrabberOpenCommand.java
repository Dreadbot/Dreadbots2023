package frc.robot.commands.grabberCommands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Grabber;

public class GrabberOpenCommand extends InstantCommand {
    private Grabber grabberPneumatic;
    private Arm arm;

    public GrabberOpenCommand(Grabber grabberPneumatic, Arm arm) {
        this.grabberPneumatic = grabberPneumatic;
        this.arm = arm;
        addRequirements(grabberPneumatic, arm);
    }
    public void execute() {
        if(!arm.isInsideBot())
            grabberPneumatic.openGrabber();
    }
}
