package frc.robot.subsystems;

import java.util.HashMap;
import java.util.List;

import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants.SwerveConstants;
import frc.robot.util.misc.DreadbotMotor;
import frc.robot.util.misc.SwerveModule;
import util.misc.DreadbotSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class Drive extends DreadbotSubsystem {
    //Double check locations
    //What is location in comparasion to front of bot 
    //Example x and y's seem to be swapped?
    private final Translation2d frontLeftLocation = new Translation2d(SwerveConstants.MODULE_X_OFFSET, SwerveConstants.MODULE_Y_OFFSET);
    private final Translation2d frontRightLocation = new Translation2d(SwerveConstants.MODULE_X_OFFSET, -SwerveConstants.MODULE_Y_OFFSET);
    private final Translation2d backLeftLocation = new Translation2d(-SwerveConstants.MODULE_X_OFFSET, SwerveConstants.MODULE_Y_OFFSET);
    private final Translation2d backRightLocation = new Translation2d(-SwerveConstants.MODULE_X_OFFSET, -SwerveConstants.MODULE_Y_OFFSET);

    private SwerveModule frontLeftModule;
    private SwerveModule frontRightModule;
    private SwerveModule backLeftModule;
    private SwerveModule backRightModule;

    private AHRS gyro;

    private SwerveDriveKinematics kinematics;

    private SwerveDriveOdometry odometry;

    private final double initialPitch;

    public Drive(AHRS gyro) {
        this.gyro = gyro;

        frontLeftModule = new SwerveModule(
            new DreadbotMotor(new CANSparkMax(1, MotorType.kBrushless), "Front Left Drive"),
            new DreadbotMotor(new CANSparkMax(2, MotorType.kBrushless), "Front Left Turn"),
            new CANCoder(9),
            SwerveConstants.FRONT_LEFT_ENCODER_OFFSET
        );
        // frontLeftModule.getDriveMotor().setInverted(true);
        frontRightModule = new SwerveModule(
            new DreadbotMotor(new CANSparkMax(3, MotorType.kBrushless), "Front Right Drive"),
            new DreadbotMotor(new CANSparkMax(4, MotorType.kBrushless), "Front Right Turn"),
            new CANCoder(10),
            SwerveConstants.FRONT_RIGHT_ENCODER_OFFSET
        );
        backLeftModule = new SwerveModule(
            new DreadbotMotor(new CANSparkMax(5, MotorType.kBrushless), "Back Left Drive"),
            new DreadbotMotor(new CANSparkMax(6, MotorType.kBrushless), "Back Left Turn"),
            new CANCoder(11),
            SwerveConstants.BACK_LEFT_ENCODER_OFFSET
        );
        // backLeftModule.getDriveMotor().setInverted(true);
        backRightModule = new SwerveModule(
            new DreadbotMotor(new CANSparkMax(7, MotorType.kBrushless), "Back Right Drive"),
            new DreadbotMotor(new CANSparkMax(8, MotorType.kBrushless), "Back Right Turn"),
            new CANCoder(12),
            SwerveConstants.BACK_RIGHT_ENCODER_OFFSET
        );

        gyro.reset();

        kinematics = new SwerveDriveKinematics(
            frontLeftLocation,
            frontRightLocation,
            backLeftLocation,
            backRightLocation
        );

        odometry  = new SwerveDriveOdometry(
            kinematics,
            gyro.getRotation2d(),
            new SwerveModulePosition[] {
                frontLeftModule.getPosition(),
                frontRightModule.getPosition(),
                backLeftModule.getPosition(),
                backRightModule.getPosition()
            }
        );

        frontLeftModule.putValuesToSmartDashboard("front left");
        frontRightModule.putValuesToSmartDashboard("front right");
        backLeftModule.putValuesToSmartDashboard("back left");
        backRightModule.putValuesToSmartDashboard("back right");

        initialPitch = gyro.getPitch();
    }
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative){
        SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(
            fieldRelative ? 
            ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, gyro.getRotation2d())
            : new ChassisSpeeds(xSpeed, ySpeed, rot)
        );
        frontLeftModule.putValuesToSmartDashboard("front left");
        frontRightModule.putValuesToSmartDashboard("front right");
        backLeftModule.putValuesToSmartDashboard("back left");
        backRightModule.putValuesToSmartDashboard("back right");

        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, SwerveConstants.ATTAINABLE_MAX_SPEED);
        setDesiredState(swerveModuleStates);
    }

    public void setDesiredState(SwerveModuleState[] swerveModuleStates) {
        frontLeftModule.setDesiredState(swerveModuleStates[0]);
        frontRightModule.setDesiredState(swerveModuleStates[1]);
        backLeftModule.setDesiredState(swerveModuleStates[2]);
        backRightModule.setDesiredState(swerveModuleStates[3]);
    }

    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(
            gyro.getRotation2d(),
            new SwerveModulePosition[] {
                frontLeftModule.getPosition(),
                frontRightModule.getPosition(),
                backLeftModule.getPosition(),
                backRightModule.getPosition()
            },
            pose
        );
    }

    public Pose2d getPosition(){
        return odometry.update(getGyroPosition(), new SwerveModulePosition[] {
            frontLeftModule.getPosition(),
            frontRightModule.getPosition(),
            backLeftModule.getPosition(),
            backRightModule.getPosition()
        });
    }

    public Rotation2d getGyroPosition() {
        return gyro.getRotation2d();
    }

    public void followSpeeds(ChassisSpeeds speeds){
        SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(speeds);

        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, SwerveConstants.ATTAINABLE_MAX_SPEED);
        frontLeftModule.setDesiredState(swerveModuleStates[0]);
        frontRightModule.setDesiredState(swerveModuleStates[1]);
        backLeftModule.setDesiredState(swerveModuleStates[2]);
        backRightModule.setDesiredState(swerveModuleStates[3]);
    }

    public Command buildAuto(HashMap<String, Command> eventMap, String pathName) {
        List<PathPlannerTrajectory> pathGroup = PathPlanner.loadPathGroup(
            pathName,
            new PathConstraints(0.7, 0.5)
        );

        SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
            this::getPosition,
            this::resetOdometry,
            kinematics,
            new PIDConstants(2.5, 0.0, 0.0),
            new PIDConstants(2, 0.0, 0.0),
            this::setDesiredState,
            eventMap,
            true,
            this
        );
        return autoBuilder.fullAuto(pathGroup).andThen(new InstantCommand());
    }

    @Override
    public void close() throws Exception {
        stopMotors();
        frontLeftModule.close();
        frontRightModule.close();
        backLeftModule.close();
        backLeftModule.close();
    }

    @Override
    public void stopMotors() {
        drive(0, 0, 0, false);
    }

    public void resetGyro(){
        gyro.reset();
    }

    public double getPitch(){
        return gyro.getPitch() - initialPitch;
    }

    public RelativeEncoder getMotorEncoder(int wheel){
        switch(wheel){
            case 1:
                return frontLeftModule.getDriveMotor().getEncoder();
            case 2:
                return frontLeftModule.getTurnMotor().getEncoder();
            case 3:
                return backLeftModule.getDriveMotor().getEncoder();
            case 4:
                return backLeftModule.getTurnMotor().getEncoder();
            case 5:
                return backRightModule.getDriveMotor().getEncoder();
            case 6:
                return backRightModule.getTurnMotor().getEncoder();
            case 7:
                return frontRightModule.getDriveMotor().getEncoder();
            case 8:
                return frontRightModule.getTurnMotor().getEncoder();
            default:
                return frontLeftModule.getDriveMotor().getEncoder();
        }
    }
    public void putValuesToSmartDashboard() {
        frontLeftModule.putValuesToSmartDashboard("front left");
        frontRightModule.putValuesToSmartDashboard("front right");
        backLeftModule.putValuesToSmartDashboard("back left");
        backRightModule.putValuesToSmartDashboard("back right");
    }
}
