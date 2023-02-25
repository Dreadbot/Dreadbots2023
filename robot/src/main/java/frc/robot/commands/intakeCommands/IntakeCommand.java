package frc.robot.commands.intakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase{
    private Intake intake;
    public IntakeCommand(Intake intake) {
        this.intake = intake;
    }
    public void execute() {
        intake.intake();
    }
    public void end(boolean interrupted) {
        intake.stopMotors();
    }
}
