package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants.ArmConstants;
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
        if(getUpperSwitch() && speed > 0) speed = 0;
        else if(getLowerSwitch() && speed < 0) speed = 0;
        // if(elevatorMotor.getPosition() > maxVal && Math.signum(speed) == 1) speed = 0;
        //  if(elevatorMotor.getPosition() < minVal && Math.signum(speed) == -1) speed = 0;
        elevatorMotor.set(speed);

    }
    public double getElevatorPosition() {
        return elevatorMotor.getPosition();
    }

    public boolean isInsideBot(){
        return elevatorMotor.getPosition() < ArmConstants.LOW_POST_POSITION - 10;
    }

    public double getMinVal() {
        return minVal;
    }

    public double getMaxVal() {
        return maxVal;
    }

    public boolean getUpperSwitch() {
        return !topSwitch.get();
    }

    public boolean getLowerSwitch() {
        zeroEncoder();
        return !lowerSwitch.get();
    }
    public void zeroEncoder() {
        if(!lowerSwitch.get() && getElevatorPosition() != 0) {
            elevatorMotor.resetEncoder();
        }
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
