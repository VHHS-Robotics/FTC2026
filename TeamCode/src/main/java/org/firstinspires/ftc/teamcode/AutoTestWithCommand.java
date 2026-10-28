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
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "AutoTestWithCommand", group = "Autonomous")
public class AutoTestWithCommand extends LinearOpMode {

    private Follower follower;

    DcMotor motor;
    private ElapsedTime runtime = new ElapsedTime();


    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 90);
    private final Pose path1 = poseFactory.of(33.9208, 8.5239, 180);
    private final Pose point2 = poseFactory.of(82.2635, 8.5387, -179.9825);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                testCommand, //inserted our test command
                follow(follower, path2())
        );
    }


    Command testCommand = Command.build()
            .setStart(() -> {
                // executed on start
                runtime.reset(); //reset the timer back to 0 second
            })
            .setExecute(() -> {
                // executed on execute
                //put some motor here getting power
            })
            .setDone(() -> {
                // return true to end the command
                return runtime.seconds() >= 5; //when this timer hits 5 seconds this boolean will be true and end the command
            })
            .setEnd(endCondition -> {
                // executed on end
                //cut the power to that motor
            });

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotor.class, "front_left");
        motor.setDirection(DcMotor.Direction.REVERSE);

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
        return Paths.line(path1, point2).reverseTangent();
    }
}
