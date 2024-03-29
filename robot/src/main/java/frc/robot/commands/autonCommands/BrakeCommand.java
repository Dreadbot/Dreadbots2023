package frc.robot.commands.autonCommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BalanceConstants;
import frc.robot.subsystems.Drive;


public class BrakeCommand extends CommandBase {
    private final Drive drive;
    private final AHRS gyro;
    public double targetPosition;

    public BrakeCommand(Drive drive, AHRS gyro) {
        this.drive = drive;
        this.gyro = gyro;
        addRequirements(drive);
    }

    int i = 0;
    @Override
    public void execute() {
    
        if( i % 25 ==0) {
            System.out.println("Target Position = " + targetPosition);
        } 
        i++;
        
        double currentPosition = drive.getMotorEncoder(1).getPosition();
        double difference = targetPosition - currentPosition;

        if(difference > 2) {
            drive.drive(BalanceConstants.MAX_SPEED + 0.1, 0, 0, false);
        } else if (difference < -2){
            drive.drive(-(BalanceConstants.MAX_SPEED + 0.1), 0, 0, false);
        } else {
            drive.drive(0, 0, 0, false);
        }
    }

    @Override
    public void initialize() {
        this.targetPosition = drive.getMotorEncoder(1).getPosition();
    }
}
