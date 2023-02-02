package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BalanceConstants;
import frc.robot.subsystems.Drive;

public class BalanceCommand extends CommandBase {
    private final Drive drive;
    private final AHRS gyro;

    public BalanceCommand(Drive drive, AHRS gyro) {
        this.drive = drive;
        this.gyro = gyro;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        double pitch = gyro.getPitch();
        double speed = pitch * BalanceConstants.SCALE;
        speed = MathUtil.clamp(speed, -BalanceConstants.MAX_SPEED, BalanceConstants.MAX_SPEED);

        if (Math.abs(pitch) < BalanceConstants.LEVEL_DEGREES) {
            speed = 0;
        }
        drive.ArcadeDrive(speed, 0, false, false);
    }

    @Override
    public boolean isFinished() {
        System.out.println("Current Pitch: " + Math.abs(gyro.getPitch()));
        return Math.abs(gyro.getPitch()) < BalanceConstants.LEVEL_DEGREES;
    }
}