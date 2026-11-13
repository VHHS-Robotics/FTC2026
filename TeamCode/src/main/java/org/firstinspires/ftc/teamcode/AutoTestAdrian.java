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
    private final Pose path2 = poseFactory.of(56.1288, 32.9412, 91.1393);
    private final Pose point3 = poseFactory.of(37.3453, 33.1731, 179.2926);
    private final Pose point4 = poseFactory.of(34.6656, 112.6167, 91.9319);
    private final Pose point5 = poseFactory.of(59.5737, 112.8144, 0.4548);
    private final Pose point6 = poseFactory.of(59.6893, 128.191, 89.5691);
    private final Pose point7 = poseFactory.of(21.6683, 90.8627, -135.5267);
    private final Pose point8 = poseFactory.of(27.543, 24.6169, -84.9323);
    private final Pose point9 = poseFactory.of(14.4868, 11.2249, 178.4884);
    private final Pose point9Control1 = poseFactory.of(30.5185, 10.8018, 0);
    private final Pose point10 = poseFactory.of(8.7556, 107.296, 93.414);

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
        return Paths.line(point1, path2).tangent();
    }

    public Path path3() {
        return Paths.line(path2, point3).tangent();
    }

    public Path path4() {
        return Paths.line(point3, point4).tangent();
    }

    public Path path5() {
        return Paths.line(point4, point5).tangent();
    }

    public Path path6() {
        return Paths.line(point5, point6).tangent();
    }

    public Path path7() {
        return Paths.line(point6, point7).tangent();
    }

    public Path path8() {
        return Paths.line(point7, point8).tangent();
    }

    public Path path9() {
        return Paths.curve(point8, point9Control1, point9).tangent();
    }

    public Path path10() {
        return Paths.line(point9, point10).tangent();
    }
}
