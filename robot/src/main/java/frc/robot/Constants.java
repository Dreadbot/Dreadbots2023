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

    private Constants() throws IllegalStateException {
        throw new IllegalStateException("Constants is a utility class. It should not be instantiated.");
    }
}