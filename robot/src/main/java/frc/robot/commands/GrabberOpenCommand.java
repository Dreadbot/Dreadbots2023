package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Grabber;

public class GrabberOpenCommand extends InstantCommand {
    private Grabber grabberPneumatic;

    public GrabberOpenCommand(Grabber grabberPneumatic) {
        this.grabberPneumatic = grabberPneumatic;
    }
    public void execute() {
        grabberPneumatic.openGrabber();
    }
}
