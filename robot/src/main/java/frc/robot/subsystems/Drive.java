package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import util.misc.DreadbotMotor;
import util.misc.DreadbotSubsystem;

public class Drive extends DreadbotSubsystem {
    private DreadbotMotor frontLeft;
    private DreadbotMotor frontRight;
    private DreadbotMotor backLeft;
    private DreadbotMotor backRight;
    private MotorControllerGroup leftMotors;
    private MotorControllerGroup rightMotors;
    private DifferentialDrive diffDrive;

    public Drive(DreadbotMotor frontLeft, DreadbotMotor frontRight, DreadbotMotor backLeft, DreadbotMotor backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.leftMotors = new MotorControllerGroup(frontLeft.getSparkMax(), backLeft.getSparkMax());
        this.rightMotors = new MotorControllerGroup(frontRight.getSparkMax(), backRight.getSparkMax());
        rightMotors.setInverted(true);
        leftMotors.setInverted(true);
        this.diffDrive = new DifferentialDrive(leftMotors, rightMotors);
    }
    public void arcadeDrive(double xSpeed, double rot) {
        diffDrive.arcadeDrive(xSpeed, rot);
    }
    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void stopMotors() {
        // TODO Auto-generated method stub
        
    }
}
