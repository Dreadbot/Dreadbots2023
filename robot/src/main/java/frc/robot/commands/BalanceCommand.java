package frc.robot.commands;

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

    public BalanceCommand(Drive drive, AHRS gyro) {
        this.drive = drive;
        this.gyro = gyro;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        pitch = gyro.getPitch();
        double speed = pitch * BalanceConstants.SCALE;
        speed = MathUtil.clamp(speed, -BalanceConstants.MAX_SPEED, BalanceConstants.MAX_SPEED);

        if (Math.abs(pitch) < BalanceConstants.LEVEL_DEGREES) {
             speed = 0;
        }

        else if (previousPitch - Math.abs(pitch) > 1 && Math.abs(pitch) > BalanceConstants.LEVEL_DEGREES){
            speed = -speed;
        }
     
        drive.ArcadeDrive(speed, 0.00d, false, false, false);
    }

    @Override
    public boolean isFinished() { 
        
        System.out.println("Current Pitch: " + Math.abs(gyro.getPitch()));
        boolean retval = previousPitch - Math.abs(pitch) > 1;
        previousPitch = Math.abs(pitch);
        //return retval;
        return false;
        //
        //return Math.abs(gyro.getPitch()) < BalanceConstants.LEVEL_DEGREES;
    }
}