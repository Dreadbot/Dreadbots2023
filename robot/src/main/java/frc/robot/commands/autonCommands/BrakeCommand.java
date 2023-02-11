package frc.robot.commands.autonCommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BalanceConstants;
import frc.robot.subsystems.Drive;


public class BrakeCommand extends CommandBase{
    private final Drive drive;
    private final AHRS gyro;
    public  double Brake;

    public BrakeCommand(Drive drive, AHRS gyro) {
        this.drive = drive;
        this.gyro = gyro;
        
        addRequirements(drive);
    }
   
int i = 0;
    @Override
    public void execute() {
    
        if( i % 25 ==0) {
            System.out.println(" Brake = " + Brake);
        } 
        i++;
        if(Math.abs(Brake -  drive.getMotorEncoder(1).getPosition()) > 0.5) {
            drive.ArcadeDrive(BalanceConstants.MAX_SPEED, 0.0, false, false, false);
        }
        else if (Math.abs(Brake - drive.getMotorEncoder(1).getPosition()) < -0.5) {
            drive.ArcadeDrive(-BalanceConstants.MAX_SPEED, 0.0, false, false, false);
        }
        else {
            drive.ArcadeDrive(0, 0.00d, false, false, false);
        }
    }
    @Override
    public void initialize() {
        this.Brake = drive.getMotorEncoder(1).getPosition();
    }
}
