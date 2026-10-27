import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class aramspath {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(130.3254, 118.7297, 90);
    private final Pose point1 = poseFactory.of(131.1625, 133.9859, 86.8596);
    private final Pose point2 = poseFactory.of(132.9045, 108.8133, 93.9589);
    private final Pose point3 = poseFactory.of(133.6753, 134.3737, 88.2727);
    private final Pose point4 = poseFactory.of(84.9502, 103.7848, -88.5595);
    private final Pose point4Control1 = poseFactory.of(84.1488, 125.7153, 0);
    private final Pose point5 = poseFactory.of(85.8823, 125.0653, -92.508);
    private final Pose point6 = poseFactory.of(84.8009, 100.3002, -92.5003);
    private final Pose point7 = poseFactory.of(133.1057, 30.7435, 89.2549);
    private final Pose point7Control1 = poseFactory.of(134.0574, 84.8139, 0);

    public Path path1() {
        return Paths.line(start, point1).tangent();
    }

    public Path path2() {
        return Paths.line(point1, point2).reverseTangent();
    }

    public Path path3() {
        return Paths.line(point2, point3).tangent();
    }

    public Path path4() {
        return Paths.curve(point3, point4Control1, point4).tangent();
    }

    public Path path5() {
        return Paths.line(point4, point5).reverseTangent();
    }

    public Path path6() {
        return Paths.line(point5, point6).tangent();
    }

    public Path path7() {
        return Paths.curve(point6, point7Control1, point7).reverseTangent();
    }
}
