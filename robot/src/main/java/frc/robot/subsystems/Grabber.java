package frc.robot.subsystems;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants.GrabberConstants;
import util.misc.DreadbotSubsystem;

public class Grabber extends DreadbotSubsystem {
    Solenoid grabberPneumatic;
    public Grabber() {
        this.grabberPneumatic = new Solenoid(PneumaticsModuleType.REVPH, GrabberConstants.GRABBER_PORT);
    }
    public void openGrabber() {
        grabberPneumatic.set(true);
    }
    public void closeGrabber() {
        grabberPneumatic.set(false);
    }
    public void toggleGrabber() {
        grabberPneumatic.set(!grabberPneumatic.get());
    }
    @Override
    public void close() throws Exception {
        stopMotors();
    }

    @Override
    public void stopMotors() {
        grabberPneumatic.close();
    }
}
