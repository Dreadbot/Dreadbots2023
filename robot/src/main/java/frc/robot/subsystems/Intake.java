package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.IntakeConstants;
import util.misc.DreadbotMotor;
import util.misc.DreadbotSubsystem;

public class Intake extends DreadbotSubsystem {
    private DreadbotMotor motor; 

    public Intake() {
        this.motor = new DreadbotMotor(new CANSparkMax((int) IntakeConstants.INTAKE_PORT, MotorType.kBrushless), "motor");
        motor.setInverted(false);
    }

    public void intake() {
        motor.set(IntakeConstants.INTAKE_SPEED);
    }

    public void outtake() {
        motor.set(-IntakeConstants.INTAKE_SPEED);
    }

    @Override
    public void close() throws Exception {
        stopMotors();
        motor.close();
    }

    @Override
    public void stopMotors() {
        motor.set(0);
    }
    
}
