// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
      public static final double WHEEL_CIRCUMFRENCE = 0.5985;
      public static final double GEAR_RATIO = 70.0 / 14.0;
      public static final double AUTON_DRIVE_SPEED = 0.25;
      public static final double SLEW_RATE_LIMIT = 0.5;
    }
    public static class MotorConstants {
      public static final int FRONT_LEFT_MOTOR_PORT = 1;
      public static final int FRONT_RIGHT_MOTOR_PORT = 2;
      public static final int BACK_LEFT_MOTOR_PORT = 3;
      public static final int BACK_RIGHT_MOTOR_PORT = 4;
    }
    public static class BalanceConstants {
      public static final double MAX_SPEED = 0.2;
      public static final double SCALE = 0.3;
      public static final double LEVEL_DEGREES = 1.25; //Angle where considered level

    }
    private Constants() throws IllegalStateException {
        throw new IllegalStateException("Constants is a utility class. It should not be instantiated.");
    }
}