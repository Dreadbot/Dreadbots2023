// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.ExampleSubsystem;
import util.misc.DreadbotMotor;
import util.controls.DreadbotController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private final DreadbotMotor frontLeftMotor = new DreadbotMotor(new CANSparkMax(1, MotorType.kBrushless), "frontLeft");
  private final DreadbotMotor frontRightMotor = new DreadbotMotor(new CANSparkMax(4, MotorType.kBrushless), "frontRight");
  private final DreadbotMotor backLeftMotor = new DreadbotMotor(new CANSparkMax(3, MotorType.kBrushless), "backLeft");
  private final DreadbotMotor backRightMotor = new DreadbotMotor(new CANSparkMax(2, MotorType.kBrushless), "backRight");
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
 private final Drive drive = new Drive(
    frontLeftMotor,
    frontRightMotor,
    backLeftMotor,
    backRightMotor
 );
  private final DreadbotController primaryController = new DreadbotController(OperatorConstants.PRIMARY_JOYSTICK_PORT);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    frontLeftMotor.setIdleMode(IdleMode.kCoast);
    frontRightMotor.setIdleMode(IdleMode.kCoast);
    backLeftMotor.setIdleMode(IdleMode.kCoast);
   // backRightMotor.setIdleMode(IdleMode.kCoast);
    configureBindings();
  }

  private void configureBindings() {
    DriveCommand driveCommand = new DriveCommand(drive, primaryController::getYAxis, primaryController::getXAxis);
    drive.setDefaultCommand(driveCommand); 
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
