package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import util.math.DreadbotMath;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriveCommandTest {


    @Test
    public void testExecuteStandard() {
//        Drive drive = Mockito.mock(Drive.class);
//        final double forward = .75;
//        final double rotation = .25;
//
//        DriveCommand driveCommand = new DriveCommand(drive, () -> forward, () -> rotation);
//
//        driveCommand.execute();
//        assertEquals(forward * .75, driveCommand.lastForward);
//        assertEquals(rotation * .75, driveCommand.lastRotation);
    }

    @Test
    public void testExecuteTurtle() {
//        Drive drive = Mockito.mock(Drive.class);
//        final double forward = .75;
//        final double rotation = .25;
//
//        DriveCommand driveCommand = new DriveCommand(drive, () -> forward, () -> rotation);
//        driveCommand.enableTurtle();
//
//        driveCommand.execute();
//        assertEquals(DreadbotMath.linearInterpolation(0.0, 0.4, forward), driveCommand.lastForward);
//        assertEquals(DreadbotMath.linearInterpolation(0.0, 0.4,rotation), driveCommand.lastRotation);
    }

    @Test
    public void testExecuteTurboDeadBand() {
//        Drive drive = Mockito.mock(Drive.class);
//        final double forward = 0.049;
//        final double rotation = .25;
//
//        DriveCommand driveCommand = new DriveCommand(drive, () -> forward, () -> rotation);
//        driveCommand.enableTurbo();
//
//        double forwardResult = DreadbotMath.linearInterpolation(0.4, 1, forward);
//
//        driveCommand.execute();
//        assertEquals(DreadbotMath.linearInterpolation(0.4, 1.0, forward), driveCommand.lastForward);
//        assertEquals(rotation * .75, driveCommand.lastRotation);
    }

}
