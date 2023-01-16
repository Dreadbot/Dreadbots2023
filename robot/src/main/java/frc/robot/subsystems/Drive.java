package frc.robot.subsystems;

import util.DreadbotMotor;

public class Drive {
    private DreadbotMotor frontLeftMotor;
    private DreadbotMotor frontRightMotor;
    private DreadbotMotor backLeftMotor;
    private DreadbotMotor backRightMotor;

    public Drive(
        DreadbotMotor frontLeftMotor,
        DreadbotMotor frontRightMotor,
        DreadbotMotor backLeftMotor,
        DreadbotMotor backRightMotor){
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backLeftMotor = backLeftMotor; 
        this.backRightMotor = backRightMotor;
    }

    
}
