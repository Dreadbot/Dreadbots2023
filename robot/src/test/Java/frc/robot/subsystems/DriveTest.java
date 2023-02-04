package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.filter.SlewRateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.misc.DreadbotMotor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DriveTest {

    private DreadbotMotor mockMotor;
    private CANSparkMax mockSparkMax;
    private RelativeEncoder mockEncoder;

    @BeforeEach
    public void setup() {
        mockMotor = mock(DreadbotMotor.class);
        mockSparkMax = mock(CANSparkMax.class);
        mockEncoder = mock(RelativeEncoder.class);
        when(mockMotor.getSparkMax()).thenReturn(mockSparkMax);

    }

    @Test
    public void testDriveArcade() throws InterruptedException {
        Drive drive = new Drive(mockMotor, mockMotor, mockMotor, mockMotor);

        drive.slewRate.reset(0);

        Thread.sleep(1000);
        double xSpeed = drive.ArcadeDrive(1, 0);
        assertEquals(.2, xSpeed, .01);

        Thread.sleep(1000);
        xSpeed = drive.ArcadeDrive(1, 0);
        assertEquals(.4, xSpeed, .01);

        Thread.sleep(1000);
        xSpeed = drive.ArcadeDrive(1, 0);
        assertEquals(.6, xSpeed, .01);
    }

    @Test
    public void testSlew() throws InterruptedException {
        SlewRateLimiter slew = new SlewRateLimiter(.1, -.1, 0);
        Thread.sleep(1000);
        assertEquals(0.1769, slew.calculate(1), 0.1);
        Thread.sleep(1000);
        assertEquals(.2, slew.calculate(1), 0.01);
        Thread.sleep(1000);
        assertEquals(.3, slew.calculate(1), 0.01);
        Thread.sleep(1000);
        assertEquals(.4, slew.calculate(1), 0.01);
        Thread.sleep(1000);
        assertEquals(.5, slew.calculate(1), 0.01);
        Thread.sleep(1000);
        assertEquals(.6, slew.calculate(1), 0.01);
        Thread.sleep(1000);
        assertEquals(.7, slew.calculate(1), 0.01);

    }
}
