// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonCommands;

import frc.robot.Robot;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.AutonomousConstants;
import frc.robot.commands.armCommands.ArmToPositionCommand;
import frc.robot.commands.grabberCommands.GrabberOpenCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Grabber;

import java.util.List;
import java.util.function.DoubleSupplier;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public final class Autos {
    /**
     * Example static factory for an autonomous command.
     */
    public static CommandBase Auton(Drive drive) {
        return new AutonDriveStraightCommand(drive, 3);
    }
    public static CommandBase ScoreAndBalance(Drive drive, Arm arm, Grabber grabber, AHRS gyro) {
      DoubleSupplier nullJoyStick = () -> 0;
      return new SequentialCommandGroup(
        new ArmToPositionCommand(arm, grabber, ArmConstants.MAX_ELEVATOR_POSITION, nullJoyStick),
        new GrabberOpenCommand(grabber),
        new ArmToPositionCommand(arm, grabber, -5, nullJoyStick),
        new AutonDriveStraightCommand(drive, 3),
        new BalanceCommand(drive, gyro),
        new BrakeCommand(drive, gyro)
      );
    }
    public static CommandBase FollowPath(Drive drive, Trajectory trajectory) {
      final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(AutonomousConstants.TRACK_WIDTH);
      final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(
        AutonomousConstants.KS_VOLTS ,
        AutonomousConstants.KV_VOLT_SECONDS_PER_METER ,
        AutonomousConstants.KA_VOLT_SECONDS_SQUARED_PER_METER 
      );
      drive.resetGyro();
      final DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
        feedforward,
        kinematics,
        10 / 2
        );
      final TrajectoryConfig config = new TrajectoryConfig(
        AutonomousConstants.MAX_SPEED_METERS_PER_SECOND,
        AutonomousConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED
      );
      config.setKinematics(kinematics);
      config.addConstraint(autoVoltageConstraint);

      // Trajectory exampleTrajectory =
      //   TrajectoryGenerator.generateTrajectory(
      //       // Start at the origin facing the +X direction
      //       new Pose2d(0, 0, new Rotation2d(0)),
      //       // Pass through these two interior waypoints, making an 's' curve path
      //       List.of(new Translation2d(0, .5)),
      //       // End 3 meters straight ahead of where we started, facing forward
      //       new Pose2d(0, 1, new Rotation2d(0)),
      //       // Pass config
      //       config);
      Trajectory forwardTrajectory =
      TrajectoryGenerator.generateTrajectory(
          // Start at the origin facing the +X direction
          new Pose2d(0, 0, new Rotation2d(0)),
          // Pass through these two interior waypoints, making an 's' curve path
          List.of(new Translation2d(0.5, 0), new Translation2d(1, 0)),
          // End 3 meters straight ahead of where we started, facing forward
          new Pose2d(1.5, 0, new Rotation2d(0)),
          // Pass config
          config);

        Trajectory sCurveTrajectory =
        TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(3, 0, new Rotation2d(0)),

            // Pass config
            config);

      RamseteCommand ramseteCommand = 
        new RamseteCommand(
          trajectory,
          drive::getPose,
          new RamseteController(AutonomousConstants.RAMSETE_B, AutonomousConstants.RAMSETE_ZETA),
          feedforward,
          kinematics,
          drive::getWheelSpeeds, 
          new PIDController(AutonomousConstants.KP_DRIVE_VELOCITY, 0, 0),
          new PIDController(AutonomousConstants.KP_DRIVE_VELOCITY, 0, 0),
          drive::TankDriveVoltage,
          drive);
      drive.resetOdometry(Robot.trajectory.getInitialPose());

      return ramseteCommand.andThen(() -> drive.TankDriveVoltage(0, 0));
    }
    private Autos() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This is a utility class!");
    }
}
