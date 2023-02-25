package util.misc;

import java.io.IOException;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;

public class DreadbotTrajectoryUtils {
    public static class DreadbotTrajectoryLoader {
        public static Trajectory loadTrajectory(String path) {
            try {
                return TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve(path));
            } catch(IOException ex) {
                DriverStation.reportError("Unable to open trajectory: " + path, ex.getStackTrace());
                return new Trajectory();
            } 
        }
    }
}
