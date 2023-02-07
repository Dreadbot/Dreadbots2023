// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.AutonomousConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GrabberCloseCommand;
import frc.robot.commands.GrabberOpenCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.TurboCommand;
import frc.robot.commands.TurtleCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Intake;
import util.controls.DreadbotController;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    private final AHRS gyro = new AHRS(SerialPort.Port.kUSB);
    private final Drive drive = new Drive(gyro);
    private final Grabber grabber = new Grabber();
    private final Intake intake = new Intake();
    private final DreadbotController primaryController = new DreadbotController(OperatorConstants.PRIMARY_JOYSTICK_PORT);
    private final DreadbotController secondaryController = new DreadbotController(OperatorConstants.SECONDARY_JOYSTICK_PORT);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();
    }

    private void configureBindings() {
        DriveCommand driveCommand = new DriveCommand(drive, primaryController::getYAxis, primaryController::getZAxis);
        drive.setDefaultCommand(driveCommand);
        primaryController.getXButton().whileTrue(new BalanceCommand(drive, gyro));
        primaryController.getLeftBumper().whileTrue(new TurtleCommand(driveCommand));
        primaryController.getRightBumper().whileTrue(new TurboCommand(driveCommand));
        primaryController.getAButton().onTrue(new GrabberOpenCommand(grabber));
        primaryController.getBButton().onTrue(new GrabberCloseCommand(grabber));
        secondaryController.getAButton().onTrue(new IntakeCommand(intake));
        secondaryController.getBButton().onTrue(new OuttakeCommand(intake));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return Autos.FollowPath(drive);
    }
    int i = 0;
    public void autonPeriodic() {
        if(i % 25 == 0) {
            System.out.println("Heading: " + drive.getHeading() + " Distance: " + (drive.getMotorEncoder(1).getPosition() / AutonomousConstants.ROTATIONS_PER_METER));
            System.out.println("X: " + drive.getPose().getX() + "Y: " + drive.getPose().getY());
        }
        i++;
    }
}
