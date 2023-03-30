package frc.robot.commands.grabberCommands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Grabber;

public class GrabberCloseCommand extends InstantCommand {
    private Grabber grabberPneumatic;

    public GrabberCloseCommand(Grabber grabberPneumatic) {
        this.grabberPneumatic = grabberPneumatic;
        addRequirements(grabberPneumatic);
    }
    public void execute() {
        grabberPneumatic.closeGrabber();
    }
}
