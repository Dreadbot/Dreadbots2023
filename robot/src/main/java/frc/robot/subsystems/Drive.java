package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
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
        frontLeftMotor = new DreadbotMotor(new CANSparkMax(1, MotorType.kBrushless), "frontLeft");
        frontRightMotor = new DreadbotMotor(new CANSparkMax(4, MotorType.kBrushless), "frontRight");
        backLeftMotor = new DreadbotMotor(new CANSparkMax(3, MotorType.kBrushless), "backLeft");
        backRightMotor = new DreadbotMotor(new CANSparkMax(2, MotorType.kBrushless), "backRight");
        
        frontLeftMotor.setIdleMode(IdleMode.kCoast);
        frontRightMotor.setIdleMode(IdleMode.kCoast);
        backLeftMotor.setIdleMode(IdleMode.kCoast);
        backRightMotor.setIdleMode(IdleMode.kCoast);

        leftMotors = new MotorControllerGroup(frontLeftMotor.getSparkMax(), backLeftMotor.getSparkMax());
        rightMotors = new MotorControllerGroup(frontRightMotor.getSparkMax(), backRightMotor.getSparkMax());

        diffDrive = new DifferentialDrive(leftMotors, rightMotors);
    }

    public void ArcadeDrive(double xSpeed, double rot) {
        diffDrive.arcadeDrive(xSpeed, rot, true);
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
