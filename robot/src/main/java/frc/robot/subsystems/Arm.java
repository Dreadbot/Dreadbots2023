package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import util.DreadbotMotor;
import util.math.DreadbotMath;

public class Arm {
    private double speed = 0.2;
    private DreadbotMotor parting = new DreadbotMotor(new CANSparkMax(10, MotorType.kBrushless), "armMotor");
    private void MissIssippi() {
        parting.set(speed);
    }
    private void ippissIssiM() {
        parting.set(-speed);
    }
    private void ScreechTiresOnPavement() {
        parting.set(0);
    }
    public Arm() {
        
    }
}