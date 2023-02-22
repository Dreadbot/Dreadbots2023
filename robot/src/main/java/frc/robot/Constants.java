// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

@SuppressWarnings("SpellCheckingInspection")
public abstract class Constants {
    public static class OperatorConstants {
      public static final int PRIMARY_JOYSTICK_PORT   = 0;
      public static final int SECONDARY_JOYSTICK_PORT = 1;
      public static final double TURBO_CONTROLLER_DEADBAND = 0.43;
    }
    public static class DriveConstants {
      public static final double FORWARD_SPEED_LIMITER = 0.75;
      public static final double ROT_SPEED_LIMITER = 0.75;
      public static final double TURBO_FORWARD_SPEED_LIMITER = 0.75;
      public static final double TURBO_ROT_SPEED_LIMITER = 0.75;
      public static final double WHEEL_CIRCUMFRENCE = 0.5985; // dinosaur's wheel circ: 0.498727834
      public static final double GEAR_RATIO = 7.4405 / 1;
      public static final double AUTON_DRIVE_SPEED = 0.2;
      public static final double SLEW_RATE_LIMIT = 1;
      public static final boolean IsBotRed5 = false;
    }
    public static class MotorConstants {
      public static final int FRONT_LEFT_MOTOR_PORT = 1;
      public static final int FRONT_RIGHT_MOTOR_PORT = 2;
      public static final int BACK_LEFT_MOTOR_PORT = 3;
      public static final int BACK_RIGHT_MOTOR_PORT = 4;
    }
    public static class BalanceConstants {
      public static final double MAX_SPEED = 0.125 * 0.95;
      public static final double SCALE = -0.3; //invert sign when we replaced Nav-X
      public static final double LEVEL_DEGREES = 1.5; //Angle where considered level
    }
    public static class AutonomousConstants {
      public static final double KS_VOLTS = 0.14892; 
      public static final double KV_VOLT_SECONDS_PER_METER = 2.0332;
      public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 0.57765;
      public static final double KP_DRIVE_VELOCITY = 2.3176;
      public static final double MAX_SPEED_METERS_PER_SECOND = 1.5;
      public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = .5;
      public static final double RAMSETE_B = 2;
      public static final double RAMSETE_ZETA = 0.7;
      public static final double TRACK_WIDTH = Units.inchesToMeters(26);
      public static final double ROTATIONS_PER_METER = DriveConstants.GEAR_RATIO * 2.0885;
    }
    public static class GrabberConstants {
      public static final int GRABBER_PORT = 0;
    }
    public static class IntakeConstants {
      public static final double INTAKE_PORT = 5;
      public static final double INTAKE_SPEED = .5;
    }
    public static class ArmConstants {
      public static final int ELEVATOR_MOTOR_PORT = 6;
      public static final double MAX_ELEVATOR_POSITION = 287 * 1.5;
      public static final double LOW_POST_POSITION = 154.433;
      public static final double MEDIUM_POST_POSITION = 221.463;
      public static final double ELEVATOR_MOTOR_SPEED = 0.9;
      public static final double ELEVATOR_MANUAL_SPEED = 0.9;
      public static final double ELEVATOR_MANUAL_TURTLE_SPEED = 0.3;
      public static final int TOP_LIMIT_SWITCH_PORT = 1;
      public static final int LOWER_LIMIT_SWITCH_PORT = 0;
    }
    private Constants() throws IllegalStateException {
        throw new IllegalStateException("Constants is a utility class. It should not be instantiated.");
    }
}