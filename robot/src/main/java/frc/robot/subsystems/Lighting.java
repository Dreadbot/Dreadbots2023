package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import util.misc.DreadbotSubsystem;

public class Lighting extends DreadbotSubsystem {
    public enum Color {
        Yellow,
        Purple
    }

    private Spark ledDriver;
    private Color currentColor;

    public Lighting() {
        this.ledDriver = new Spark(0);
        currentColor = Color.Yellow;
        SmartDashboard.putBoolean("Requested Piece", false);
    }

    public void setColor(Color color) {
        if(color == Color.Yellow) {
            ledDriver.set(0.63);
            currentColor = Color.Yellow;
            SmartDashboard.putBoolean("Requested Piece", true);
        } else if(color == Color.Purple) {
            ledDriver.set(0.91);
            currentColor = Color.Purple;
            SmartDashboard.putBoolean("Requested Piece", falseg);
        }
    }

    public Color getCurrentColor() {
        return this.currentColor;
    }
    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stopMotors() {
       
        
    }
    
}
