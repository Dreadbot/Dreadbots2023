package frc.robot.util.misc;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.SwerveConstants;
import frc.robot.util.misc.DreadbotMotor;

public class SwerveModule {

    private DreadbotMotor driveMotor;
    private DreadbotMotor turningMotor;
    private CANCoder turningCanCoder;
    private double canCoderOffset;
    private SparkMaxPIDController drivePIDController;
    private PIDController turningPIDController = new PIDController(2, 0, 0);
    
    public SwerveModule(DreadbotMotor driveMotor, DreadbotMotor turnMotor, CANCoder turningCanCoder, double canCoderOffset) {
        this.driveMotor = driveMotor;
        this.turningMotor = turnMotor;
        this.turningCanCoder = turningCanCoder;
        this.turningCanCoder.configMagnetOffset(-canCoderOffset); // even after passing in the correct offsets, this STILL refuses to work, so we just subtract the offset manually
        this.canCoderOffset = canCoderOffset;
        // this.turningMotor.setInverted(true);
        this.drivePIDController = driveMotor.getPIDController();
        this.drivePIDController.setP(0.1);
        this.drivePIDController.setFF(1);

        this.driveMotor.getEncoder().setPositionConversionFactor(SwerveConstants.WHEEL_DIAMETER * Math.PI * SwerveConstants.DRIVE_GEAR_RATIO); //convert from revolutions to meters
        this.driveMotor.getEncoder().setVelocityConversionFactor((SwerveConstants.WHEEL_DIAMETER * Math.PI * SwerveConstants.DRIVE_GEAR_RATIO) / 60);
        turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
            driveMotor.getEncoder().getVelocity(), 
            new Rotation2d(Units.degreesToRadians(turningCanCoder.getAbsolutePosition()))
        );
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            driveMotor.getEncoder().getPosition(), 
            new Rotation2d(Units.degreesToRadians(turningCanCoder.getAbsolutePosition()))
        );
    }

    public void resetEncoder() {
        driveMotor.resetEncoder();
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        SwerveModuleState optimizedState = SwerveModuleState.optimize(desiredState, new Rotation2d(Units.degreesToRadians(turningCanCoder.getAbsolutePosition())));
        driveMotor.setP(SmartDashboard.getNumber("Drive P", 0));
        driveMotor.setI(SmartDashboard.getNumber("Drive I", 0));
        driveMotor.setD(SmartDashboard.getNumber("Drive D", 0));
        driveMotor.setFF(SmartDashboard.getNumber("Drive FF", 0));

        double turnOutput = turningPIDController.calculate(Units.degreesToRadians(turningCanCoder.getAbsolutePosition()), optimizedState.angle.getRadians());
        drivePIDController.setReference(optimizedState.speedMetersPerSecond, ControlType.kVelocity);
        turningMotor.setVoltage(turnOutput);
    }

    public void putValuesToSmartDashboard(String name) {
        SmartDashboard.putNumber(name +" Can Coder", turningCanCoder.getPosition());
    }

    public DreadbotMotor getDriveMotor() {
        return this.driveMotor;
    }

    public DreadbotMotor getTurnMotor() {
        return this.turningMotor;
    }

    public void close() throws Exception{
        driveMotor.close();
        turningMotor.close();
    }
}