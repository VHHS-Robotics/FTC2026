package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "AutoPath", group = "Autonomous")
public class P3_G extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(53.522, 8.3825, 90);
    private final Pose path1 = poseFactory.of(53.5381, 25.802, 90);
    private final Pose point2Start = poseFactory.of(53.5381, 25.802, 90);
    private final Pose point2 = poseFactory.of(34.2368, 47.506, 90);
    private final Pose point3Start = poseFactory.of(34.2368, 47.506, -90);
    private final Pose point3 = poseFactory.of(54.6831, 106.2862, -90);
    private final Pose point3Control1 = poseFactory.of(22.0961, 95.6588, 0);
    private final Pose point3Control2 = poseFactory.of(40.3971, 107.9464, 0);
    private final Pose point4Start = poseFactory.of(54.6831, 106.2862, 180);
    private final Pose point4 = poseFactory.of(32.044, 8.0266, 180);
    private final Pose point4Control1 = poseFactory.of(13.6392, 113.7645, 0);
    private final Pose point4Control2 = poseFactory.of(34.2186, 36.7864, 0);
    private final Pose point4Control3 = poseFactory.of(21.1571, 36.1552, 0);
    private final Pose point5Start = poseFactory.of(32.044, 8.0266, 180);
    private final Pose point5 = poseFactory.of(13.7914, 7.6909, 180);
    private final Pose point6Start = poseFactory.of(13.7914, 7.6909, 90);
    private final Pose point6 = poseFactory.of(53.8759, 25.6432, 90);
    private final Pose point7Start = poseFactory.of(53.8759, 25.6432, 90);
    private final Pose point7 = poseFactory.of(34.365, 47.4071, 90);
    private final Pose point8Start = poseFactory.of(34.365, 47.4071, -90);
    private final Pose point8 = poseFactory.of(54.7276, 106.4436, -90);
    private final Pose point8Control1 = poseFactory.of(21.0201, 106.8904, 0);
    private final Pose point9Start = poseFactory.of(54.7276, 106.4436, -90);
    private final Pose point9 = poseFactory.of(9.266, 105.8277, -90);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4()),
                follow(follower, path5()),
                follow(follower, path6()),
                follow(follower, path7()),
                follow(follower, path8()),
                follow(follower, path9())
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
        return Paths.line(point2Start, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.curve(point3Start, point3Control1, point3Control2, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.curve(point4Start, point4Control1, point4Control2, point4Control3, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return Paths.line(point5Start, point5).linear(point5Start, point5);
    }

    public Path path6() {
        return Paths.line(point6Start, point6).linear(point6Start, point6);
    }

    public Path path7() {
        return Paths.line(point7Start, point7).linear(point7Start, point7);
    }

    public Path path8() {
        return Paths.curve(point8Start, point8Control1, point8).linear(point8Start, point8);
    }
    public Path path9() {
        return Paths.line(point9Start, point9).linear(point9Start, point9);
    }
}
