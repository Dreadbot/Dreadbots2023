package frc.robot.commands.virtualCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Lighting;
import frc.robot.subsystems.Lighting.Color;

public class ToggleLightingCommand extends InstantCommand {
    private Lighting lighting;
    public ToggleLightingCommand(Lighting lighting) {
        this.lighting = lighting;
        addRequirements(lighting);
    }
    @Override
    public void execute() {
        lighting.setColor(lighting.getCurrentColor() == Color.Purple ? Color.Yellow : Color.Purple);
    }
}
