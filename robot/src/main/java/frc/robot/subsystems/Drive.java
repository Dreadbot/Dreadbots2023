package frc.robot.subsystems;

import org.opencv.features2d.FastFeatureDetector;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Constants.DriveConstants;
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

    protected final SlewRateLimiter slewRate;
    private final DifferentialDriveOdometry odometry;

    private final AHRS gryo;

    public Drive(AHRS gryo) {
        this.frontLeftMotor = new DreadbotMotor(new CANSparkMax(MotorConstants.FRONT_LEFT_MOTOR_PORT, MotorType.kBrushless), "frontLeft");
        this.frontRightMotor = new DreadbotMotor(new CANSparkMax(MotorConstants.FRONT_RIGHT_MOTOR_PORT, MotorType.kBrushless), "frontRight");
        this.backLeftMotor = new DreadbotMotor(new CANSparkMax(MotorConstants.BACK_LEFT_MOTOR_PORT, MotorType.kBrushless), "backLeft");
        this.backRightMotor = new DreadbotMotor(new CANSparkMax(MotorConstants.BACK_RIGHT_MOTOR_PORT, MotorType.kBrushless), "backRight");
        
        frontLeftMotor.setIdleMode(IdleMode.kBrake);
        frontRightMotor.setIdleMode(IdleMode.kBrake);
        backLeftMotor.setIdleMode(IdleMode.kBrake);
        backRightMotor.setIdleMode(IdleMode.kBrake);

        leftMotors = new MotorControllerGroup(frontLeftMotor.getSparkMax(), backLeftMotor.getSparkMax());
        rightMotors = new MotorControllerGroup(frontRightMotor.getSparkMax(), backRightMotor.getSparkMax());

        leftMotors.setInverted(true);
        rightMotors.setInverted(false);
        diffDrive = new DifferentialDrive(leftMotors, rightMotors);
        this.gryo = gryo;
        odometry = new DifferentialDriveOdometry(
            gryo.getRotation2d(),
            frontLeftMotor.getEncoder().getPosition() * 0.1588,
            frontRightMotor.getEncoder().getPosition() * 0.1588
        );
        slewRate = new SlewRateLimiter(DriveConstants.SLEW_RATE_LIMIT, -DriveConstants.SLEW_RATE_LIMIT, 0.2);
    }

    // Constructor for testing, allows injection of mock motors
    public Drive(DreadbotMotor fl, DreadbotMotor fr, DreadbotMotor bl, DreadbotMotor br) {
        this.frontLeftMotor = fl;
        this.frontRightMotor = fr;
        this.backLeftMotor = bl;
        this.backRightMotor = br;
        this.gryo = null;
        this.odometry = null;
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
        slewRate = new SlewRateLimiter(DriveConstants.SLEW_RATE_LIMIT, -DriveConstants.SLEW_RATE_LIMIT, 0.2);
    }
    @Override
    public void periodic() {
        odometry.update(
            gryo.getRotation2d(),
            frontLeftMotor.getEncoder().getPosition() * 0.1588,
            frontRightMotor.getEncoder().getPosition() * 0.1588
        );
    }
    public double ArcadeDrive(double xSpeed, double rot) {
        xSpeed = addSlewRate(xSpeed);
        diffDrive.arcadeDrive(xSpeed, rot, true);
        return xSpeed;
    }

    public double ArcadeDrive(double xSpeed, double rot, boolean squareSpeed) {
        xSpeed = addSlewRate(xSpeed);
        diffDrive.arcadeDrive(xSpeed, rot, squareSpeed);
        return xSpeed;
    }

    public void CurvatureDrive(double xSpeed, double rot) {
        diffDrive.curvatureDrive(addSlewRate(xSpeed), rot, true);
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

    private double addSlewRate(double joystickAxis){
        return slewRate.calculate(joystickAxis);
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(
            gryo.getRotation2d(),
            frontLeftMotor.getEncoder().getPosition() * 0.1588,
            frontRightMotor.getEncoder().getPosition() * 0.1588,
            pose
        );
    }
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
           ((frontLeftMotor.getEncoder().getVelocity() / 60) / 2.32) * 0.1588,
           ((frontRightMotor.getEncoder().getVelocity() / 60) / 2.32) * 0.1588
        );
    }
    public void resetEncoders() {
        frontLeftMotor.resetEncoder();
        frontRightMotor.resetEncoder();
        backLeftMotor.resetEncoder();
        backRightMotor.resetEncoder();
    }
    public double getHeading() {
        return gryo.getRotation2d().getDegrees();
    }
    public Pose2d getPose() {
        return odometry.getPoseMeters();
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
