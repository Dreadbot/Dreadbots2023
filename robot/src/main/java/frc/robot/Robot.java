// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import util.misc.BufferedPrint;
import util.misc.DreadbotPowerLogger;
import util.misc.DreadbotTrajectoryUtils.DreadbotTrajectoryLoader;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private Command autonomousCommand;
    private RobotContainer robotContainer;

    static String exitTurnRightJSON = "paths/output/ExitTurnRight.wpilib.json";
    static String exitTurnLeftJSON = "paths/output/ExitTurnLeft.wpilib.json";
    static String exitTurnAroundToRightJSON = "paths/output/ExitTurnAroundToRight.wpilib.json";
    static String exitTurnAroundToLeftJSON = "paths/output/ExitTurnAroundToLeft.wpilib.json";
    static String pickupCubeLeftSideJSON = "paths/output/PickupCubeLeftSide.wpilib.json";
    static String returnToGridLeftSideJSON = "paths/output/ReturnToGridLeftSide.wpilib.json";
    static String scoreAndLeaveJSON = "paths/output/ScoreAndLeave.wpilib.json";
    static String returnToBalanceJSON = "paths/output/ReturnToBalance.wpilib.json";



    public static Trajectory exitTurnRightTrajectory = DreadbotTrajectoryLoader.loadTrajectory(exitTurnRightJSON);
    public static Trajectory exitTurnLeftTrajectory = DreadbotTrajectoryLoader.loadTrajectory(exitTurnLeftJSON);
    public static Trajectory exitTurnAroundToRightTrajectory = DreadbotTrajectoryLoader.loadTrajectory(exitTurnAroundToRightJSON);
    public static Trajectory exitTurnAroundToLeftTrajectory = DreadbotTrajectoryLoader.loadTrajectory(exitTurnAroundToLeftJSON);
    public static Trajectory pickupCubeLeftSideTrajectory = DreadbotTrajectoryLoader.loadTrajectory(pickupCubeLeftSideJSON);
    public static Trajectory returnToGridLeftSideTrajectory = DreadbotTrajectoryLoader.loadTrajectory(returnToGridLeftSideJSON);
    public static Trajectory scoreAndLeaveTrajectory = DreadbotTrajectoryLoader.loadTrajectory(scoreAndLeaveJSON);
    public static Trajectory returnToBalanceTrajectory = DreadbotTrajectoryLoader.loadTrajectory(returnToBalanceJSON);






    private final PowerDistribution pdh = new PowerDistribution(10, ModuleType.kRev);

    //private final DreadbotPowerLogger powerLogger = new DreadbotPowerLogger(pdh);

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        robotContainer = new RobotContainer();
        //pdh.setSwitchableChannel(false);
    }

    /**
     * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
        //powerLogger.logCurrents();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
        //pdh.setSwitchableChannel(true);
        //powerLogger.stopLogging();
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        autonomousCommand = robotContainer.getAutonomousCommand();
        
        autonomousCommand = robotContainer.getAutonomousCommand();

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        robotContainer.autonPeriodic();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        robotContainer.teleopPeriodic();
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    /**
     * This function is called once when the robot is first started up.
     */
    @Override
    public void simulationInit() {
    }

    /**
     * This function is called periodically whilst in simulation.
     */
    @Override
    public void simulationPeriodic() {
    }
}
