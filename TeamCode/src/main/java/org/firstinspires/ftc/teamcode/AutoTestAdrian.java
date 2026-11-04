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
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

@Autonomous(name = "AutoTestAdrian", group = "Autonomous")
public class AutoTestAdrian extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56.6002, 9.2176, 90);
    private final Pose point1 = poseFactory.of(56.5904, 9.7328, 91.0926);
    private final Pose path2 = poseFactory.of(45.5006, 35.086, 89.9799);
    private final Pose path2Control1 = poseFactory.of(58.5153, 23.9008, 0);
    private final Pose point3 = poseFactory.of(36.6683, 40.6151, 147.9534);
    private final Pose point4 = poseFactory.of(39.7199, 112.1562, 73.1764);
    private final Pose point4Control1 = poseFactory.of(27.8882, 73.026, 0);
    private final Pose point5 = poseFactory.of(54.8662, 108.6772, -76.3793);
    private final Pose point5Control1 = poseFactory.of(52.3238, 119.1965, 0);
    private final Pose point6 = poseFactory.of(52.8606, 130.0338, 88.8162);
    private final Pose point6Control1 = poseFactory.of(72.6251, 121.5968, 0);
    private final Pose point7 = poseFactory.of(21.6683, 90.8627, -128.5306);
    private final Pose point7Control1 = poseFactory.of(15.3147, 116.4761, 0);
    private final Pose point8 = poseFactory.of(27.543, 24.6169, -84.9323);
    private final Pose point9 = poseFactory.of(17.2359, 13.3542, -132.4632);
    private final Pose point9Control1 = poseFactory.of(33.0456, 11.9904, 0);
    private final Pose point10 = poseFactory.of(8.8761, 107.4077, 95.0793);
    private final Pose point10Control1 = poseFactory.of(11.3854, 53.0075, 0);

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
                follow(follower, path9()),
                follow(follower, path10())
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
        return Paths.line(start, point1).tangent();
    }

    public Path path2() {
        return Paths.curve(point1,path2Control1,path2).tangent();
    }

    public Path path3() {
        return Paths.line(path2, point3).tangent();
    }

    public Path path4() {
        return Paths.curve(point3,point4Control1,point4).tangent();
    }

    public Path path5() {
        return Paths.curve(point4,point5Control1,point5).tangent();
    }

    public Path path6() {
        return Paths.curve(point5,point6Control1,point6).tangent();
    }

    public Path path7() { return Paths.curve(point6,point7Control1,point7).tangent();
    }

    public Path path8() {
        return Paths.line(point7, point8).tangent();
    }

    public Path path9() {
        return Paths.curve(point8,point9Control1,point9).tangent();
    }

    public Path path10() {
        return Paths.curve(point9,point10Control1,point10).tangent();
    }
}