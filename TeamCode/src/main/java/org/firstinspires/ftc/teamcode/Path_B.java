package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Path_B", group = "Autonomous")
public class Path_B extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 90);
    private final Pose path1 = poseFactory.of(56, 35, 90);
    private final Pose point2 = poseFactory.of(55, 110, -24.801);
    private final Pose point2Control1 = poseFactory.of(43.3405, 51.7361, 0);
    private final Pose point2Control2 = poseFactory.of(42.4155, 0.209, 0);
    private final Pose point2Control3 = poseFactory.of(36.3742, 0.0113, 0);
    private final Pose point2Control4 = poseFactory.of(25.7565, 3.3802, 0);
    private final Pose point2Control5 = poseFactory.of(32.4842, 14.3664, 0);
    private final Pose point2Control6 = poseFactory.of(14.6463, 12.8403, 0);
    private final Pose point2Control7 = poseFactory.of(0, 0, 0);
    private final Pose point2Control8 = poseFactory.of(0, 0, 0);
    private final Pose point2Control9 = poseFactory.of(14.6616, 11.0417, 0);
    private final Pose point2Control10 = poseFactory.of(6.9774, 13.2165, 0);
    private final Pose point2Control11 = poseFactory.of(8.5911, 74.0217, 0);
    private final Pose point2Control12 = poseFactory.of(30.0066, 121.5491, 0);
    private final Pose point3Start = poseFactory.of(55, 110, 270);
    private final Pose point3 = poseFactory.of(55, 110, 270);
    private final Pose point4Start = poseFactory.of(55, 110, 0);
    private final Pose point4 = poseFactory.of(10, 110, 0);
    private final Pose point5Start = poseFactory.of(10, 110, 270);
    private final Pose point5 = poseFactory.of(10, 110, 270);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4()),
                follow(follower, path5())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.curve(path1, point2Control1, point2Control2, point2Control3, point2Control4, point2Control5, point2Control6, point2Control7, point2Control8, point2Control9, point2Control10, point2Control11, point2Control12, point2).tangent();
    }

    public Path path3() {
        return Paths.line(point3Start, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.line(point4Start, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return Paths.line(point5Start, point5).linear(point5Start, point5);
    }
}
