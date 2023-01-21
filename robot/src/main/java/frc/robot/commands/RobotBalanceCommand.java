package frc.robot.commands;

import java.util.function.DoubleSupplier;

import com.kauailabs.navx.frc.AHRS;

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
        drive.setFrontRightSpeed(joystickForwardAxis.getAsDouble());
        drive.setFrontLeftSpeed(joystickForwardAxis.getAsDouble());
        drive.setBackRightSpeed(joystickForwardAxis.getAsDouble());
        drive.setBackLeftSpeed(joystickForwardAxis.getAsDouble());
        double maxspeed = 0.4;
        double pitch = 0;
        double roll = 0;
        double yaw = 0;
       /* System.out.println("X axis" + controller.getXAxis());
        System.out.println("Y axis" + controller.getYAxis());*/
    
        /*if (joystickForwardAxis.getAsDouble() > .1) {
          drive.setFrontRightSpeed(joystickForwardAxis.getAsDouble());
          drive.setFrontLeftSpeed(-joystickForwardAxis.getAsDouble());
          drive.setBackRightSpeed(joystickForwardAxis.getAsDouble());
          drive.setBackLeftSpeed(-joystickForwardAxis.getAsDouble());
          if (joystickRotationalAxis.getAsDouble() > maxspeed) {
            drive.setFrontRightSpeed(maxspeed);
            drive.setFrontLeftSpeed(-maxspeed);
            drive.setBackRightSpeed(maxspeed);
            drive.setBackLeftSpeed(-maxspeed);
          }
        } else if (joystickRotationalAxis.getAsDouble() < -.1) {
            drive.setFrontRightSpeed(joystickRotationalAxis.getAsDouble());
            drive.setFrontLeftSpeed(-joystickRotationalAxis.getAsDouble());
            drive.setBackRightSpeed(joystickRotationalAxis.getAsDouble());
            drive.setBackLeftSpeed(-joystickRotationalAxis.getAsDouble());
            if (joystickRotationalAxis.getAsDouble() < -maxspeed) {
            drive.setFrontRightSpeed(-maxspeed);
            drive.setFrontLeftSpeed(maxspeed);
            drive.setBackRightSpeed(-maxspeed);
            drive.setBackLeftSpeed(maxspeed);
            } 
        } else if (joystickForwardAxis.getAsDouble() > maxspeed) {
          drive.setFrontRightSpeed(maxspeed);
          drive.setFrontLeftSpeed(maxspeed);
          drive.setBackRightSpeed(maxspeed);
          drive.setBackLeftSpeed(maxspeed);
        } else if (joystickForwardAxis.getAsDouble() < -maxspeed) {
          drive.setFrontRightSpeed(-maxspeed);
          drive.setFrontLeftSpeed(-maxspeed);
          drive.setBackRightSpeed(-maxspeed);
          drive.setBackLeftSpeed(-maxspeed);
        } */
          pitch = gyro.getPitch();
          // System.out.println("Pitch" + pitch);
           yaw = gyro.getYaw();
           //System.out.println("Yaw" + yaw);
           roll = gyro.getRoll();
           System.out.println("Roll" + roll + 
           "  Yaw" + yaw + "  Pitch" + pitch);
           
           double scale = .008;
           double speed = 0;
       
           speed = pitch * scale;
    
           if (speed > 0.1) {
             speed = 0.1;
           }
           if (speed < -0.1){
             speed = -0.1;
           }
    
           if(Math.abs(pitch)<2){speed=0;}
       
           drive.setFrontRightSpeed(speed);
           drive.setFrontLeftSpeed(speed);
           drive.setBackRightSpeed(speed);
           drive.setBackLeftSpeed(speed);
       
           /*SmartDashboard.putNumber("Pitch", pitch);
           SmartDashboard.putNumber("Yaw", yaw);
           SmartDashboard.putNumber("Roll", roll);
           SmartDashboard.putNumber("Speed", speed); */
           


    }
}
