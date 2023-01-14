// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static final I2C.Port        I2C_PORT       = I2C.Port.kOnboard;
  public static final SerialPort.Port GYROSCOPE_PORT = SerialPort.Port.kUSB;
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  //private CANSparkMax motor = new CANSparkMax(1, MotorType.kBrushless);
  private DreadbotMotor motor = new DreadbotMotor(new CANSparkMax(1, MotorType.kBrushless), "Motor");
  private DreadbotMotor motor3 = new DreadbotMotor(new CANSparkMax(3, MotorType.kBrushless), "Motor3");
  private DreadbotMotor motor10 = new DreadbotMotor(new CANSparkMax(10, MotorType.kBrushless), "Motor10");
  private DreadbotMotor motor2 = new DreadbotMotor(new CANSparkMax(2, MotorType.kBrushless), "Motor10");
  private AHRS gyro = new AHRS(SerialPort.Port.kMXP);
  private double pitch = 0;
  private double yaw = 0;
  private double roll = 0;
  private DreadbotController controller = new DreadbotController(0);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    motor.set(controller.getYAxis());
    motor10.set(controller.getYAxis());
    motor3.set(controller.getYAxis());
    motor2.set(controller.getYAxis());
    double maxspeed = 0.4;
   /* System.out.println("X axis" + controller.getXAxis());
    System.out.println("Y axis" + controller.getYAxis());*/

    if (controller.getXAxis() > .1) {
      motor.set(controller.getXAxis());
      motor10.set(-controller.getXAxis());
      motor3.set(controller.getXAxis());
      motor2.set(-controller.getXAxis());
      if (controller.getXAxis() > maxspeed) {
        motor.set(maxspeed);
        motor10.set(-maxspeed);
        motor3.set(maxspeed);
        motor2.set(-maxspeed);
      }
    } else if (controller.getXAxis() < -.1) {
      motor.set(controller.getXAxis());
      motor10.set(-controller.getXAxis());
      motor3.set(controller.getXAxis());
      motor2.set(-controller.getXAxis());
        if (controller.getXAxis() < -maxspeed) {
          motor.set(-maxspeed);
          motor10.set(maxspeed);
          motor3.set(-maxspeed);
          motor2.set(maxspeed);
        } 
    } else if (controller.getYAxis() > maxspeed) {
      motor.set(maxspeed);
      motor10.set(maxspeed);
      motor3.set(maxspeed);
      motor2.set(maxspeed);
    } else if (controller.getYAxis() < -maxspeed) {
      motor.set(-maxspeed);
      motor10.set(-maxspeed);
      motor3.set(-maxspeed);
      motor2.set(-maxspeed);
    } 
    if (controller.isXButtonPressed()){
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
   
       motor.set(speed);
       motor10.set(speed);
       motor3.set(speed);
       motor2.set(speed);
   
       SmartDashboard.putNumber("Pitch", pitch);
       SmartDashboard.putNumber("Yaw", yaw);
       SmartDashboard.putNumber("Roll", roll);
       SmartDashboard.putNumber("Speed", speed);
    }    
    

   /* if (pitch > 5) {
    motor.set(.1);
    motor10.set(.1);
    motor3.set(.1);
    motor2.set(.1);
    }
    if (pitch < -5) {
    motor.set(-.1);
    motor10.set(-.1);
    motor3.set(-.1);
    motor2.set(-.1);
    }
    */

    /*
    if(controller.isDpadUpPressed()) {
      motor.set(.2);
      motor3.set(.2);
      motor10.set(.2);
    } else if(controller.isDpadDownPressed()) {
      motor.set(-.2);
      motor3.set(-.2);
      motor10.set(-.2);
    } else if(controller.isDpadUpPressed()) {
      motor.set(-.2);
      motor3.set(.2);
      motor10.set(-.2);
    } else if(controller.isLeftTriggerPressed()) {
      motor.set(.2);
      motor3.set(-.2);
      motor10.set(.2);
    } else {
      motor.set(0);
      motor3.set(0);
      motor10.set(0);
    } 
    */
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
