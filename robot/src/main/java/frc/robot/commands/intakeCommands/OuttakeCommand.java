package frc.robot.commands.intakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class OuttakeCommand extends CommandBase{
    private Intake outtake;
    public OuttakeCommand(Intake outtake) {
        this.outtake = outtake;
    }
    public void execute() {
        outtake.outtake();
    }
    @Override
    public void end(boolean interrupted) {
        outtake.stopMotors();
    }
}