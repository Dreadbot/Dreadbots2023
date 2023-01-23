package frc.robot.commands;

import java.util.function.DoubleSupplier;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class RobotBalanceCommand extends CommandBase {
    private final Drive drive;
    private final DoubleSupplier joystickForwardAxis;
    private final DoubleSupplier joystickRotationalAxis;
    private final AHRS gyro;
    public RobotBalanceCommand(Drive drive, DoubleSupplier joystickForwardAxis, DoubleSupplier joystickRotationalAxis, AHRS gyro) {
        this.drive = drive;
        this.joystickForwardAxis = joystickForwardAxis;
        this.joystickRotationalAxis = joystickRotationalAxis;
        this.gyro = gyro;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        double maxspeed = 0.3;
        double pitch = gyro.getPitch(); 
        double scale = -0.5;
        double speed = pitch * scale;
        speed = MathUtil.clamp(speed, -maxspeed, maxspeed);

        System.out.println(Math.abs(gyro.getPitch()));

        if(Math.abs(pitch) < 1.25) {
          speed = 0;
        }
        drive.ArcadeDrive(speed, 0);
    }
    @Override
    public boolean isFinished() {
      return Math.abs(gyro.getPitch()) < 1.25;
    }
}