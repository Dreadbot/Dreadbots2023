package frc.robot.commands.armCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Grabber;
import frc.robot.util.math.DreadbotMath;

public class ArmCommand extends CommandBase {
    private Arm arm;
    private Grabber grabber;
    private DoubleSupplier joystickValue;
    private JoystickButton overideClose;
    private JoystickButton turtleMode;
    public ArmCommand(Arm arm, Grabber grabber,  DoubleSupplier joystickValue, JoystickButton overideClose, JoystickButton turtleMode) {
        this.arm = arm;
        this.grabber = grabber;
        this.joystickValue = joystickValue;
        this.overideClose = overideClose;
        this.turtleMode = turtleMode;
        addRequirements(arm);
    }
    int i = 0;
    @Override
    public void execute() {
        if(overideClose.getAsBoolean()) {
            grabber.closeGrabber();
        } else if(arm.getLowerSwitch() && joystickValue.getAsDouble() < 0.10){
            grabber.openGrabber();
        } else if (Math.abs(joystickValue.getAsDouble()) > 0.10 && arm.getElevatorPosition() < ArmConstants.INSIDE_BOT_POSITION) {
            grabber.closeGrabber(); 
        }
        
        double speed = DreadbotMath.applyDeadbandToValue(joystickValue.getAsDouble(), 0.08);
        double elevatorPosition = arm.getElevatorPosition();
        if(elevatorPosition < ArmConstants.PICKUP_ELEVATOR_POSITION - 3 || elevatorPosition > ArmConstants.MAX_ELEVATOR_POSITION - 10) {
            speed = speed * 0.6; //slow down bot if reaching the botton
        }
        arm.elevate(speed * (turtleMode.getAsBoolean() ? ArmConstants.ELEVATOR_MANUAL_TURTLE_SPEED : ArmConstants.ELEVATOR_MANUAL_SPEED));
    }
}
