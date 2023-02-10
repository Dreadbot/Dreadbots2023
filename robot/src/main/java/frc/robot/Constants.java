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
      public static final double TURBO_FORWARD_SPEED_LIMITER = 0.6;
      public static final double TURBO_ROT_SPEED_LIMITER = 0.6;
      public static final double WHEEL_CIRCUMFRENCE = 0.5985;
      public static final double GEAR_RATIO = 70.0 / 14.0;
      public static final double AUTON_DRIVE_SPEED = 0.25;
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
      public static final double MAX_SPEED = 0.115;
      public static final double SCALE = -0.3;
      public static final double LEVEL_DEGREES = 1.25; //Angle where considered level
    }
    public static class AutonomousConstants {
      public static final double KS_VOLTS = 0.164; // Original Value: 0.27038
      public static final double KV_VOLT_SECONDS_PER_METER = 0.0467; // Original Value: 1.8425
      public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 0.0784; // Original Value: 0.61532
      public static final double KP_DRIVE_VELOCITY = 0.366; // Original Value: 2.7049
      public static final double MAX_SPEED_METERS_PER_SECOND = 1;
      public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = .5;
      public static final double RAMSETE_B = 2;
      public static final double RAMSETE_ZETA = 0.7;
      public static final double TRACK_WIDTH = Units.inchesToMeters(27.737); // Original Value: 0.685
      public static final double ROTATIONS_PER_METER = 14.4685;
    }
    public static class GrabberConstants {
      public static final int GRABBER_PORT = 0; // change this to real CAN ID later
    }
    public static class IntakeConstants {
      public static final double INTAKE_PORT = 5; // is 5
      public static final double INTAKE_SPEED = .5;
    }
    public static class ArmConstants {
      public static final int ELEVATOR_MOTOR_PORT = 6;
      public static final int MAX_ELEVATOR_POSITION = 287;
      public static final int MIN_ELEVATOR_POSITION = 0;
      public static final double ELEVATOR_MOTOR_SPEED = 0.3;
      public static final double ELEVATOR_SPEED_LIMITER = 0.4;
      public static final int TOP_LIMIT_SWITCH_PORT = 0;
      public static final int LOWER_LIMIT_SWITCH_PORT = 1;
    }
    private Constants() throws IllegalStateException {
        throw new IllegalStateException("Constants is a utility class. It should not be instantiated.");
    }
}