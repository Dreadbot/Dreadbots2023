// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

@SuppressWarnings("SpellCheckingInspection")
public abstract class Constants {
    public static class OperatorConstants {
      public static final int PRIMARY_JOYSTICK_PORT   = 0;
      public static final int SECONDARY_JOYSTICK_PORT = 1;
    }
    public static class DriveConstants {
      public static final double FORWARD_SPEED_LIMITER = 0.75f;
      public static final double ROT_SPEED_LIMITER = 0.75f;
    }
    public static class MotorConstants {
      public static final int FRONT_LEFT_MOTOR_PORT = 1;
      public static final int FRONT_RIGHT_MOTOR_PORT = 4;
      public static final int BACK_LEFT_MOTOR_PORT = 3;
      public static final int BACK_RIGHT_MOTOR_PORT = 2;
    }
    private Constants() throws IllegalStateException {
        throw new IllegalStateException("Constants is a utility class. It should not be instantiated.");
    }
}