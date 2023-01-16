package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import util.DreadbotMotor;

public class Drive {
    private DifferentialDrive diffDrive;

    private MotorControllerGroup leftMotors;
        private DreadbotMotor frontLeftMotor;
        private DreadbotMotor frontRightMotor;
    private MotorControllerGroup rightMotors;
        private DreadbotMotor backLeftMotor;
        private DreadbotMotor backRightMotor;

    public Drive(
        DreadbotMotor frontLeftMotor,
        DreadbotMotor frontRightMotor,
        DreadbotMotor backLeftMotor,
        DreadbotMotor backRightMotor){
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        leftMotors = new MotorControllerGroup(frontLeftMotor.getSparkMax(), backLeftMotor.getSparkMax());
        this.backLeftMotor = backLeftMotor; 
        this.backRightMotor = backRightMotor;
        rightMotors = new MotorControllerGroup(frontRightMotor.getSparkMax(), backRightMotor.getSparkMax());

        diffDrive = new DifferentialDrive(leftMotors, rightMotors);
    }

    
}
