// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.GrabberConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.armCommands.ArmCommand;
import frc.robot.commands.armCommands.ArmToPositionCommand;
import frc.robot.commands.autonCommands.AutoAlignConeCommand;
import frc.robot.commands.autonCommands.AutoAlignCubeCommand;
//import frc.robot.commands.autonCommands.Autos;
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
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.HashMap;

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
    private HashMap<String, Command> autonEvents = new HashMap();
    private SendableChooser<Command> autonChooser = new SendableChooser<Command>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        autonEvents.put("retract-arm", new ArmToPositionCommand(arm, grabber, ArmConstants.MIN_ELEVATOR_POSITION, () -> 0.0));
        autonEvents.put("extend-arm", new ArmToPositionCommand(arm, grabber, ArmConstants.MAX_ELEVATOR_POSITION, () -> 0.0));
        autonEvents.put("grab", new GrabberCloseCommand(grabber)
            .andThen(new GrabberWaitCommand(GrabberConstants.WAIT_PERIOD, grabber)
            .andThen(new ArmToPositionCommand(arm, grabber, ArmConstants.PICKUP_ELEVATOR_POSITION, secondaryController::getYAxis))));
        autonEvents.put("score", new ArmToPositionCommand(arm, grabber, ArmConstants.MAX_ELEVATOR_POSITION, () -> 0.0)
                .andThen(new GrabberWaitCommand(GrabberConstants.WAIT_PERIOD, grabber))
                .andThen(new GrabberOpenCommand(grabber, arm)));
    
        autonChooser.setDefaultOption(
            "Score, Leave, and Balance", drive.buildAuto(autonEvents, "ScoreLeaveBalance"));
        autonChooser.addOption("Partial Link Bump", drive.buildAuto(autonEvents, "PartialLinkBump"));
        autonChooser.addOption("Partial Link No Bump", drive.buildAuto(autonEvents, "PartialLinkNonBump"));
        SmartDashboard.putData(autonChooser);
        // Configure the trigger bindings
        configureBindings();
    }
    private void configureBindings() {
        DriveCommand driveCommand = new DriveCommand(drive, primaryController::getYAxis,primaryController::getXAxis, primaryController::getZAxis);
        ArmCommand armCommand = new ArmCommand(arm, grabber, secondaryController::getYAxis, secondaryController.getLeftTrigger(), secondaryController.getLeftBumper());
        DefaultGrabberOpenCommand grabberOpenCommand = new DefaultGrabberOpenCommand(grabber, arm);
        drive.setDefaultCommand(driveCommand);
        arm.setDefaultCommand(armCommand);
        grabber.setDefaultCommand(grabberOpenCommand);
        primaryController.getXButton().whileTrue(new BalanceCommand(drive, gyro).andThen(new BrakeCommand(drive, gyro)));
        primaryController.getLeftBumper().whileTrue(new TurtleCommand(driveCommand));
        primaryController.getRightBumper().whileTrue(new TurboCommand(driveCommand));
        primaryController.getLeftTrigger().whileTrue(new OuttakeCommand(intake));
        primaryController.getRightTrigger().whileTrue(new IntakeCommand(intake));
        primaryController.getAButton().onTrue(new AutoAlignConeCommand(drive));
        primaryController.getBButton().onTrue(new AutoAlignCubeCommand(drive));
        secondaryController.getLeftTrigger().whileTrue(new GrabberCloseCommand(grabber)); //needed for extra conditions were we want to close no matter what
        secondaryController.getRightTrigger().onTrue((new GrabberOpenCommand(grabber, arm)
            .andThen(new GrabberWaitCommand(GrabberConstants.WAIT_PERIOD, grabber))
            .andThen(new ArmToPositionCommand(arm, grabber, -5, secondaryController::getYAxis)))
                .unless(arm::isInsideBot));
        secondaryController.getStartButton().onTrue(new CameraCommand(smartDashboard));
        secondaryController.getAButton().onTrue(new GrabberCloseCommand(grabber)
            .andThen(new GrabberWaitCommand(GrabberConstants.WAIT_PERIOD, grabber)
            .andThen(new ArmToPositionCommand(arm, grabber, ArmConstants.PICKUP_ELEVATOR_POSITION, secondaryController::getYAxis))));
        secondaryController.getBButton().onTrue(new GrabberCloseCommand(grabber)
            .andThen(new GrabberWaitCommand(GrabberConstants.WAIT_PERIOD, grabber).unless(arm::getNotLowerSwitch))
            .andThen(new ArmToPositionCommand(arm, grabber, ArmConstants.LOW_POST_POSITION, secondaryController::getYAxis)));
        secondaryController.getXButton().onTrue(new GrabberCloseCommand(grabber)
            .andThen(new GrabberWaitCommand(GrabberConstants.WAIT_PERIOD, grabber).unless(arm::getNotLowerSwitch))
            .andThen(new ArmToPositionCommand(arm, grabber, ArmConstants.MEDIUM_POST_POSITION, secondaryController::getYAxis)));
        secondaryController.getYButton().onTrue(new GrabberCloseCommand(grabber)
            .andThen(new GrabberWaitCommand(GrabberConstants.WAIT_PERIOD, grabber).unless(arm::getNotLowerSwitch))
            .andThen(new ArmToPositionCommand(arm, grabber, ArmConstants.MAX_ELEVATOR_POSITION, secondaryController::getYAxis)));

        //Autos.generateCommands(drive, arm, grabber, gyro, intake);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return autonChooser.getSelected();
        
    }

    public void autonPeriodic() {

    }
   
    public void teleopPeriodic() {
        SmartDashboard.putBoolean("Lower Limit switch", arm.getLowerSwitch());
        SmartDashboard.putBoolean("Upper Limit switch", arm.getUpperSwitch());
    }
}
