package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        this.elevatorMotor.setIdleMode(IdleMode.kBrake);
        this.minVal = this.elevatorMotor.getPosition();
        this.maxVal = this.elevatorMotor.getPosition() + ArmConstants.MAX_ELEVATOR_POSITION;
        this.topSwitch = new DigitalInput(ArmConstants.TOP_LIMIT_SWITCH_PORT);
        this.lowerSwitch = new DigitalInput(ArmConstants.LOWER_LIMIT_SWITCH_PORT);
    }
    
    public void elevate(double speed) {
        if(getUpperSwitch() && speed > 0) speed = 0;
        else if(getLowerSwitch() && speed < 0) speed = 0;
        SmartDashboard.putNumber("Arm Speed", speed);
        elevatorMotor.set(speed);

    }
    public double getElevatorPosition() {
        return elevatorMotor.getPosition();
    }

    public boolean isInsideBot(){
        return elevatorMotor.getPosition() < ArmConstants.INSIDE_BOT_POSITION;
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

    /*
     * <p> Why does wpi not have the opposite of an unless
     */
    public boolean getNotLowerSwitch(){
        return !getLowerSwitch();
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
