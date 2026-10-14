package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class P3_G {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 90);
    private final Pose path1 = poseFactory.of(55.4021, 26.2345, 90);
    private final Pose point2 = poseFactory.of(35.4746, 48.0676, 90);
    private final Pose point3 = poseFactory.of(34.2563, 105.7868, -90);
    private final Pose point4 = poseFactory.of(57.099, 106.5186, -90);
    private final Pose point5 = poseFactory.of(33.9738, 105.8308, -90);
    private final Pose point6Start = poseFactory.of(33.9738, 105.8308, 90);
    private final Pose point6 = poseFactory.of(34.3106, 13.3684, 180);
    private final Pose point7 = poseFactory.of(8.2843, 8.5834, 180);
    private final Pose point8 = poseFactory.of(55.2202, 26.5388, 90);
    private final Pose point9 = poseFactory.of(34.8887, 48.2495, 90);
    private final Pose point10Start = poseFactory.of(34.8887, 48.2495, 180);
    private final Pose point10 = poseFactory.of(33.6904, 106.6591, -90);
    private final Pose point11 = poseFactory.of(56.7174, 106.5186, -90);
    private final Pose point12 = poseFactory.of(6.4814, 106.0956, -90);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.line(path1, point2).linear(path1, point2);
    }

    public Path path3() {
        return Paths.line(point2, point3).linear(point2, point3);
    }

    public Path path4() {
        return Paths.line(point3, point4).linear(point3, point4);
    }

    public Path path5() {
        return Paths.line(point4, point5).linear(point4, point5);
    }

    public Path path6() {
        return Paths.line(point6Start, point6).linear(point6Start, point6);
    }

    public Path path7() {
        return Paths.line(point6, point7).linear(point6, point7);
    }

    public Path path8() {
        return Paths.line(point7, point8).linear(point7, point8);
    }

    public Path path9() {
        return Paths.line(point8, point9).linear(point8, point9);
    }

    public Path path10() {
        return Paths.line(point10Start, point10).linear(point10Start, point10);
    }

    public Path path11() {
        return Paths.line(point10, point11).linear(point10, point11);
    }

    public Path path12() {
        return Paths.line(point11, point12).linear(point11, point12);
    }
}