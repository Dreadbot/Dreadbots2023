package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.ArmConstants;
import util.misc.DreadbotMotor;
import util.misc.DreadbotSubsystem;

public class Arm extends DreadbotSubsystem {
    private DreadbotMotor elevatorMotor;

    public Arm() {
        this.elevatorMotor = new DreadbotMotor(new CANSparkMax(ArmConstants.ELEVATOR_MOTOR_PORT, MotorType.kBrushless), "Elevator");
    }

    public void elevate(double speed) {
        if(elevatorMotor.getPosition() >= ArmConstants.MAX_ELEVATOR_POSITION) speed = 0;
        if(elevatorMotor.getPosition() <= ArmConstants.MIN_ELEVATOR_POSITION) speed = 0;
        elevatorMotor.set(speed);
    }

    public double getElevatorPosition() {
        return elevatorMotor.getPosition();
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
