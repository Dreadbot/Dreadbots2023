package frc.robot.subsystems;

import javax.imageio.plugins.tiff.TIFFDirectory;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.armCommands.ArmCommand;
import util.misc.DreadbotMotor;
import util.misc.DreadbotSubsystem;

public class Arm extends DreadbotSubsystem {
    private DreadbotMotor elevatorMotor;
    private double minVal;
    private double maxVal;
    private DigitalInput topSwitch;
    private DigitalInput lowerSwitch;

    public Arm() {
        this.elevatorMotor = new DreadbotMotor(new CANSparkMax(ArmConstants.ELEVATOR_MOTOR_PORT, MotorType.kBrushless), "Elevator");
        this.elevatorMotor.setInverted(true);
        this.minVal = this.elevatorMotor.getPosition();
        this.maxVal = this.elevatorMotor.getPosition() + ArmConstants.MAX_ELEVATOR_POSITION;
        this.topSwitch = new DigitalInput(ArmConstants.TOP_LIMIT_SWITCH_PORT);
        this.lowerSwitch = new DigitalInput(ArmConstants.LOWER_LIMIT_SWITCH_PORT);
    }

    public void elevate(double speed) {
        if(getTopSwitch() == true && speed > 0) speed = 0;
        else if(getLowerSwitch() == true && speed < 0) speed = 0;
        // if(elevatorMotor.getPosition() > maxVal && Math.signum(speed) == 1) speed = 0;
        //  if(elevatorMotor.getPosition() < minVal && Math.signum(speed) == -1) speed = 0;
        elevatorMotor.set(speed);

    }
    public double getElevatorPosition() {
        return elevatorMotor.getPosition();
    }

    public double getMinVal() {
        return minVal;
    }

    public double getMaxVal() {
        return maxVal;
    }

    public boolean getTopSwitch(){
        return !topSwitch.get();
    }

    public boolean getLowerSwitch(){
        return !lowerSwitch.get();
    }

    @Override
    public void close() throws Exception {
        stopMotors();
        elevatorMotor.close();
    }

    @Override
    public void stopMotors() {
        elevatorMotor.stopMotor();
    }
    
}
