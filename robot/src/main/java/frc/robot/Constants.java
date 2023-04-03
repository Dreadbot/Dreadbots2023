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
      public static final double STRAFE_SPEED_LIMITER = 0.75;
      public static final double ROT_SPEED_LIMITER = 0.75;
      public static final double TURBO_FORWARD_SPEED_LIMITER = 0.75;
      public static final double TURBO_ROT_SPEED_LIMITER = 0.75;
      public static final double WHEEL_CIRCUMFRENCE = 0.53449; // dinosaur's wheel circ: 0.498727834
      public static final double GEAR_RATIO = 6.25 / 1;
      public static final double AUTON_DRIVE_SPEED = 0.25;
      public static final double SLEW_RATE_LIMIT = 1;
      public static final double TURTLE_MODE_MAX_SPEED = 0.4;
      public static final double TURBO_MODE_MIN_SPEED = TURTLE_MODE_MAX_SPEED;
      public static final boolean IsBotRed5 = false;
      public static final boolean IS_FIELD_ORIENTED = true;
      public static final double ANGLE_GAIN = 0.002;
    }
    // public static class MotorConstants {
    //   public static final int FRONT_LEFT_MOTOR_PORT = 1;
    //   public static final int FRONT_RIGHT_MOTOR_PORT = 3;
    //   public static final int BACK_LEFT_MOTOR_PORT = 5;
    //   public static final int BACK_RIGHT_MOTOR_PORT = 7;
    // }
    public static class BalanceConstants {
      public static final double MAX_SPEED = 0.125;
      public static final double SCALE = -0.3; //invert sign when we replaced Nav-X
      public static final double LEVEL_DEGREES = 11; //Angle where considered level (11)
    }
    public static class AutonomousConstants {
      public static final double KS_VOLTS = 0.15117; 
      public static final double KV_VOLT_SECONDS_PER_METER = 1.5553;
      public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 0.40129;
      public static final double KP_DRIVE_VELOCITY = 0.63592;
      public static final double MAX_SPEED_METERS_PER_SECOND = 1.5;
      public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = .75;
      public static final double RAMSETE_B = 2;
      public static final double RAMSETE_ZETA = 0.7;
      public static final double TRACK_WIDTH = Units.inchesToMeters(26);
      public static final double ROTATIONS_PER_METER = DriveConstants.GEAR_RATIO * 2.0885;
    }
    public static class GrabberConstants {
      public static final int GRABBER_PORT = 0;
      public static final double WAIT_PERIOD = .35;
    }
    public static class IntakeConstants {
      public static final double INTAKE_PORT = 13;
      public static final double INTAKE_SPEED = .25;
    }
    public static class ArmConstants {
      public static final int ELEVATOR_MOTOR_PORT = 14;
      public static final double MAX_ELEVATOR_POSITION = 168.42; // make sure to hit limit switch
      public static final double MIN_ELEVATOR_POSITION = -5;
      public static final double LOW_POST_POSITION = 154.433 * 0.75 * 0.75;
      public static final double INSIDE_BOT_POSITION = LOW_POST_POSITION;
      public static final double MEDIUM_POST_POSITION = 221.463 * 0.75 * 0.75;
      public static final double PICKUP_ELEVATOR_POSITION = 30 * 0.75 * 0.75;
      public static final double ELEVATOR_MOTOR_SPEED = 1;
      public static final double ELEVATOR_MANUAL_SPEED = 1;
      public static final double ELEVATOR_MANUAL_TURTLE_SPEED = 0.5;
      public static final int TOP_LIMIT_SWITCH_PORT = 1;
      public static final int LOWER_LIMIT_SWITCH_PORT = 0;
    }
    public static class SwerveConstants {
      public static final double ATTAINABLE_MAX_SPEED = 1.25;
      public static final double MODULE_Y_OFFSET = Units.inchesToMeters(26.0) / 2; // Between the front and back
      public static final double MODULE_X_OFFSET = Units.inchesToMeters(23.0) / 2; // Between the left and right
      // Encoder offsets are in degrees, not radians
      public static final double FRONT_LEFT_ENCODER_OFFSET = -137.021 + 180 + 3;
      public static final double FRONT_RIGHT_ENCODER_OFFSET = -31.816 - 4;
      public static final double BACK_LEFT_ENCODER_OFFSET = -116.895 + 4;
      public static final double BACK_RIGHT_ENCODER_OFFSET = -121.904 + 180 + 4;
      public static final double DRIVE_GEAR_RATIO = (14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0);
      public static final double TURN_GEAR_RATIO = 150 / 7;
      public static final double WHEEL_DIAMETER = Units.inchesToMeters(4.0);
    }
}