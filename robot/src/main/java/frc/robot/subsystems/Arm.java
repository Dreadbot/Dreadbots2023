package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.ArmConstants;
import util.misc.DreadbotMotor;
import util.misc.DreadbotSubsystem;

public class Arm extends DreadbotSubsystem {
    private DreadbotMotor elevatorMotor;
    private double minVal;
    private double maxVal;

    public Arm() {
        this.elevatorMotor = new DreadbotMotor(new CANSparkMax(ArmConstants.ELEVATOR_MOTOR_PORT, MotorType.kBrushless), "Elevator");
        this.minVal = this.elevatorMotor.getPosition();
        this.maxVal = this.elevatorMotor.getPosition() + ArmConstants.MAX_ELEVATOR_POSITION;
    }

    public void elevate(double speed) {
        if(elevatorMotor.getPosition()>= maxVal) speed = 0;
        if(elevatorMotor.getPosition() <= minVal) speed = 0;
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

    @Override
    public void close() throws Exception {
        stopMotors();
    }

    @Override
    public void stopMotors() {
        elevatorMotor.stopMotor();
    }
    
}
