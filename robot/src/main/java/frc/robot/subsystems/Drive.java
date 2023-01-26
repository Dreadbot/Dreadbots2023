package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Constants.MotorConstants;
import util.misc.DreadbotMotor;
import util.misc.DreadbotSubsystem;

public class Drive extends DreadbotSubsystem {
    private final DifferentialDrive diffDrive;
    
    private final DreadbotMotor frontLeftMotor;
    private final DreadbotMotor frontRightMotor;
    private final DreadbotMotor backLeftMotor;
    private final DreadbotMotor backRightMotor;

    private final MotorControllerGroup leftMotors;
    private final MotorControllerGroup rightMotors;

    public Drive() {

        this.frontLeftMotor = new DreadbotMotor(new CANSparkMax(MotorConstants.FRONT_LEFT_MOTOR_PORT, MotorType.kBrushless), "frontLeft");
        this.frontRightMotor = new DreadbotMotor(new CANSparkMax(MotorConstants.FRONT_RIGHT_MOTOR_PORT, MotorType.kBrushless), "frontRight");
        this.backLeftMotor = new DreadbotMotor(new CANSparkMax(MotorConstants.BACK_LEFT_MOTOR_PORT, MotorType.kBrushless), "backLeft");
        this.backRightMotor = new DreadbotMotor(new CANSparkMax(MotorConstants.BACK_RIGHT_MOTOR_PORT, MotorType.kBrushless), "backRight");
        
        frontLeftMotor.setIdleMode(IdleMode.kBrake);
        frontRightMotor.setIdleMode(IdleMode.kBrake);
        backLeftMotor.setIdleMode(IdleMode.kBrake);
        backRightMotor.setIdleMode(IdleMode.kBrake);

        frontLeftMotor.setInverted(false);
        backLeftMotor.setInverted(false);
        frontRightMotor.setInverted(true);
        backRightMotor.setInverted(true);

        leftMotors = new MotorControllerGroup(frontLeftMotor.getSparkMax(), backLeftMotor.getSparkMax());
        rightMotors = new MotorControllerGroup(frontRightMotor.getSparkMax(), backRightMotor.getSparkMax());

        diffDrive = new DifferentialDrive(leftMotors, rightMotors);
    }

    public void ArcadeDrive(double xSpeed, double rot) {
        diffDrive.arcadeDrive(xSpeed, rot, true);
    }

    public void ArcadeDrive(double xSpeed, double rot, boolean squareSpeed) {
        diffDrive.arcadeDrive(xSpeed, rot, squareSpeed);
    }

    public void CurvatureDrive(double xSpeed, double rot) {
        diffDrive.curvatureDrive(xSpeed, rot, true);
    }

    public void TankDrive(double ySpeed, double wSpeed) { // WUMBO SPEED
        diffDrive.tankDrive(ySpeed, wSpeed);
    }

    public void TankDriveVoltage(double yVolts, double wVolts) {
        leftMotors.setVoltage(yVolts);
        rightMotors.setVoltage(wVolts);
        diffDrive.feed();
    }

    public RelativeEncoder getMotorEncoder(int wheel) {
        switch(wheel) {
            case 1:
                return frontLeftMotor.getEncoder();
            case 2:
                return frontRightMotor.getEncoder();
            case 3:
                return backLeftMotor.getEncoder();
            case 4:
                return backRightMotor.getEncoder();
            default:
                return frontLeftMotor.getEncoder();
        }
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void stopMotors() {
        leftMotors.stopMotor();
        rightMotors.stopMotor();
    }

}
