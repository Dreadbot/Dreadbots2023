// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.armCommands.ArmCommand;
import frc.robot.commands.armCommands.ArmToPositionCommand;
import frc.robot.commands.autonCommands.AutoAlignConeCommand;
import frc.robot.commands.autonCommands.AutoAlignCubeCommand;
import frc.robot.commands.autonCommands.Autos;
import frc.robot.commands.autonCommands.BalanceCommand;
import frc.robot.commands.autonCommands.BrakeCommand;
import frc.robot.commands.driveCommands.DriveCommand;
import frc.robot.commands.driveCommands.TurboCommand;
import frc.robot.commands.driveCommands.TurtleCommand;
import frc.robot.commands.grabberCommands.DefaultGrabberOpenCommand;
import frc.robot.commands.grabberCommands.GrabberCloseCommand;
import frc.robot.commands.grabberCommands.GrabberOpenCommand;
import frc.robot.commands.grabberCommands.GrabberWaitCommand;
import frc.robot.commands.intakeCommands.IntakeCommand;
import frc.robot.commands.intakeCommands.OuttakeCommand;
import frc.robot.commands.virtualCommands.CameraCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Intake;
import util.controls.DreadbotController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    private final AHRS gyro = new AHRS(SerialPort.Port.kUSB1);
    private final Drive drive = new Drive(gyro);
    private final Grabber grabber = new Grabber();
    private final Intake intake = new Intake();
    private final Arm arm = new Arm();
    private final DreadbotController primaryController = new DreadbotController(OperatorConstants.PRIMARY_JOYSTICK_PORT);
    private final DreadbotController secondaryController = new DreadbotController(OperatorConstants.SECONDARY_JOYSTICK_PORT);
    private final NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
    private final NetworkTable smartDashboard = ntInstance.getTable("SmartDashboard");
    SendableChooser<Integer> autonChooser = new SendableChooser<Integer>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        
        autonChooser.setDefaultOption("Score and Balance", 1);
        autonChooser.addOption("Score and Leave", 2);
        autonChooser.addOption("Score, Leave, Balance", 3);
        SmartDashboard.putData(autonChooser);
        // Configure the trigger bindings
        configureBindings();
    }
    private void configureBindings() {
        DriveCommand driveCommand = new DriveCommand(drive, primaryController::getYAxis, primaryController::getZAxis, arm::getElevatorPosition);
        ArmCommand armCommand = new ArmCommand(arm, grabber, secondaryController::getYAxis, secondaryController.getLeftTrigger(), secondaryController.getLeftBumper());
        DefaultGrabberOpenCommand grabberOpenCommand = new DefaultGrabberOpenCommand(grabber, arm);
        drive.setDefaultCommand(driveCommand);
        arm.setDefaultCommand(armCommand);
        //grabber.setDefaultCommand(grabberOpenCommand);
        primaryController.getXButton().whileTrue(new BalanceCommand(drive, gyro).andThen(new BrakeCommand(drive, gyro)));
        primaryController.getLeftBumper().whileTrue(new TurtleCommand(driveCommand));
        primaryController.getRightBumper().whileTrue(new TurboCommand(driveCommand));
        primaryController.getLeftTrigger().whileTrue(new OuttakeCommand(intake));
        primaryController.getRightTrigger().whileTrue(new IntakeCommand(intake));
        primaryController.getAButton().onTrue(new AutoAlignConeCommand(drive));
        primaryController.getBButton().onTrue(new AutoAlignCubeCommand(drive));
        secondaryController.getLeftTrigger().onTrue(new GrabberCloseCommand(grabber).andThen(new WaitCommand(.5)));
        secondaryController.getRightTrigger().onTrue(new GrabberOpenCommand(grabber));
        secondaryController.getStartButton().onTrue(new CameraCommand(smartDashboard));
        secondaryController.getAButton().onTrue(new GrabberCloseCommand(grabber).andThen(new GrabberWaitCommand(0.25, grabber).andThen(new ArmToPositionCommand(arm, grabber, 30, secondaryController::getYAxis))));
        secondaryController.getBButton().onTrue(new ArmToPositionCommand(arm, grabber, ArmConstants.LOW_POST_POSITION, secondaryController::getYAxis));
        secondaryController.getXButton().onTrue(new ArmToPositionCommand(arm, grabber, ArmConstants.MEDIUM_POST_POSITION, secondaryController::getYAxis));
        secondaryController.getYButton().onTrue(new ArmToPositionCommand(arm, grabber, ArmConstants.MAX_ELEVATOR_POSITION, secondaryController::getYAxis));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        int chosenAuton = autonChooser.getSelected();
        // An example command will be run in autonomous
        switch(chosenAuton) {
            case 1:
                return Autos.ScoreAndBalance(drive, arm, grabber, gyro);
            case 2:
                return Autos.ScoreAndLeave(drive, arm, grabber);
            case 3:
                return null;
            default:
                return Autos.ScoreAndBalance(drive, arm, grabber, gyro);
        }
    }
    int i = 0;
    public void autonPeriodic() {
        // if(i % 25 == 0) {
        //     System.out.println("Heading: " + drive.getHeading() + " Distance: " + (drive.getMotorEncoder(1).getPosition() / AutonomousConstants.ROTATIONS_PER_METER));
        //     System.out.println("X: " + drive.getPose().getX() + "Y: " + drive.getPose().getY());
        // }
        // i++;
    }
    // int j =oipfd 0;
    public void teleopPeriodic() {
        // if(j % 25 == 0) {
        //     System.out.println(gyro.getPitch());
        // }

        // j++;
    }
}
