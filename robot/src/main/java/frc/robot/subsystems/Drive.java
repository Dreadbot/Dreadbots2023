package frc.robot.subsystems;


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
import frc.robot.Constants.AutonomousConstants;
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
    protected final SlewRateLimiter turboSlewRate;
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

        frontLeftMotor.setInverted(false);
        frontRightMotor.setInverted(true);
        backLeftMotor.setInverted(false);
        backRightMotor.setInverted(true);
        

        leftMotors = new MotorControllerGroup(frontLeftMotor.getSparkMax(), backLeftMotor.getSparkMax());
        rightMotors = new MotorControllerGroup(frontRightMotor.getSparkMax(), backRightMotor.getSparkMax());

        leftMotors.setInverted(false);
        rightMotors.setInverted(false);
        diffDrive = new DifferentialDrive(leftMotors, rightMotors);
        this.gryo = gryo;
        odometry = new DifferentialDriveOdometry(
            gryo.getRotation2d(),
            (frontLeftMotor.getEncoder().getPosition() / AutonomousConstants.ROTATIONS_PER_METER),
            (frontRightMotor.getEncoder().getPosition() / AutonomousConstants.ROTATIONS_PER_METER)
        );
        slewRate = new SlewRateLimiter(DriveConstants.SLEW_RATE_LIMIT, -DriveConstants.SLEW_RATE_LIMIT, 0.2);
        turboSlewRate = new SlewRateLimiter(DriveConstants.TURBO_FORWARD_SPEED_LIMITER, -DriveConstants.TURBO_FORWARD_SPEED_LIMITER, .4);
    }

    @Override
    public void periodic() {
        odometry.update(
            gryo.getRotation2d(),
            (frontLeftMotor.getEncoder().getPosition() / AutonomousConstants.ROTATIONS_PER_METER),
            (frontRightMotor.getEncoder().getPosition() / AutonomousConstants.ROTATIONS_PER_METER)
        );
    }
    public double ArcadeDrive(double xSpeed, double rot) {
        return ArcadeDrive(xSpeed, rot, true, true, false);
    }

    public double ArcadeDrive(double xSpeed, double rot, boolean squareSpeed, boolean addSlew, boolean turboMode) {
        if(addSlew) {
            xSpeed = addSlewRate(xSpeed);
            if(turboMode)
                xSpeed = addTurboSlewRate(xSpeed);
        }
        diffDrive.arcadeDrive(xSpeed, rot, squareSpeed);
        return xSpeed;
    }

    public void CurvatureDrive(double xSpeed, double rot) {
        diffDrive.curvatureDrive(addSlewRate(xSpeed), rot, true);
    }

    public void TankDrive(double ySpeed, double wSpeed) { // WUMBO SPEED
        diffDrive.tankDrive(ySpeed, wSpeed);
    }
    int j = 0;
    public void TankDriveVoltage(double yVolts, double wVolts) {
        leftMotors.setVoltage(yVolts);
        rightMotors.setVoltage(wVolts);
        if(j % 10 == 0) {
            System.out.println("left volts: " + yVolts);
            System.out.println("right volts: " + wVolts);
        }
        j++;
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

    private double addTurboSlewRate(double joystickAxis){
        return turboSlewRate.calculate(joystickAxis);
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(
            gryo.getRotation2d(),
            (frontLeftMotor.getEncoder().getPosition() / AutonomousConstants.ROTATIONS_PER_METER),
            (frontRightMotor.getEncoder().getPosition() / AutonomousConstants.ROTATIONS_PER_METER),
            pose
        );
    }
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
           ((frontLeftMotor.getEncoder().getVelocity() / 60) / AutonomousConstants.ROTATIONS_PER_METER),
           ((frontRightMotor.getEncoder().getVelocity() / 60) / AutonomousConstants.ROTATIONS_PER_METER)
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

    public void resetGyro() {
        gryo.reset();
    }
    @Override
    public void close() throws Exception {
        stopMotors();
        frontLeftMotor.close();
        frontRightMotor.close();
        backLeftMotor.close();
        backRightMotor.close();
    }

    @Override
    public void stopMotors() {
        leftMotors.stopMotor();
        rightMotors.stopMotor();
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
        turboSlewRate = new SlewRateLimiter(DriveConstants.TURBO_FORWARD_SPEED_LIMITER, -DriveConstants.TURBO_FORWARD_SPEED_LIMITER, .4);
    }
}
