// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.AutonomousConstants;
import frc.robot.subsystems.Drive;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.CommandBase;

public final class Autos {
    /**
     * Example static factory for an autonomous command.
     */
    public static CommandBase Auton(Drive drive) {
        return new AutonDriveStraightCommand(drive, 3);
    }
    public static CommandBase FollowPath(Drive drive) {
      final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(AutonomousConstants.TRACK_WIDTH);
      final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(
        AutonomousConstants.KS_VOLTS,
        AutonomousConstants.KV_VOLT_SECONDS_PER_METER,
        AutonomousConstants.KA_VOLT_SECONDS_SQUARED_PER_METER
      );
      final DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
        feedforward,
        kinematics,
        10
        );
      final TrajectoryConfig config = new TrajectoryConfig(
        AutonomousConstants.MAX_SPEED_METERS_PER_SECOND,
        AutonomousConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED
      );
      config.setKinematics(kinematics);
      config.addConstraint(autoVoltageConstraint);
    }
    private Autos() {
        throw new UnsupportedOperationException("This is a utility class!");
    }
}
