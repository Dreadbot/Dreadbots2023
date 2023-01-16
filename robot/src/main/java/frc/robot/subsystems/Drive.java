package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

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

    public void ArcadeDrive(double xSpeed, double rot) {
        diffDrive.arcadeDrive(xSpeed, rot, true);
    }

    public void CurvatureDrive(double xSpeed, double rot) {
        diffDrive.curvatureDrive(xSpeed, rot, true);
    }

    public void TankDrive(double ySpeed, double wSpeed ) { //WUMBO SPEED
        diffDrive.tankDrive(ySpeed, wSpeed);
    }

    
    
}
