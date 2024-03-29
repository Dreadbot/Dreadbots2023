package frc.robot.commands.autonCommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BalanceConstants;
import frc.robot.subsystems.Drive;

public class BalanceCommand extends CommandBase {
    private final Drive drive;
    private final AHRS gyro;
    private double previousPitch = 15;
    private double pitch;
    int i = 0;

    public BalanceCommand(Drive drive, AHRS gyro) {
        this.drive = drive;
        this.gyro = gyro;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        pitch = drive.getPitch();
        double speed = pitch * BalanceConstants.SCALE;
        speed = MathUtil.clamp(speed, -BalanceConstants.MAX_SPEED, BalanceConstants.MAX_SPEED);

        if (Math.abs(pitch) < BalanceConstants.LEVEL_DEGREES) {
            System.out.println(pitch);
            speed = 0;
        } else if (Math.abs(previousPitch) - Math.abs(pitch) > 0.5 && Math.abs(pitch) > BalanceConstants.LEVEL_DEGREES){
            speed = -speed;
        }
        
        System.out.println(speed);
     
        // if(i % 25 == 0) {
        //     SmartDashboard.putNumber("Pitch", pitch);
        //     SmartDashboard.putNumber("speed", speed);
        //     System.out.println(" previous pitch" + previousPitch + " Pitch" + Math.abs(pitch));
        // }
          
        // i++;

        drive.drive(speed, 0.00d, 0.0, false);
    }

    @Override
    public boolean isFinished() { 
        previousPitch = Math.abs(pitch);
        // return Math.abs(drive.getPitch()) < BalanceConstants.LEVEL_DEGREES;
        return false;
    }
}