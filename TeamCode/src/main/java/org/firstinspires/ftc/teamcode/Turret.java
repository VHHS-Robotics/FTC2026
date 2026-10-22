package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;


@Autonomous(name = "BioBuzz_30Sec_Auton", group = "Autonomous")
public class Turret extends LinearOpMode {

    // Hardware Declare
    private Follower follower;
    private Limelight3A limelight;
    private DcMotor turretMotor;
    private DigitalChannel lightSensor;

    // Configuration / Constants
    private static final int PIPELINE_APRILTAG = 0; // Configured AprilTag pipeline
    private static final int PIPELINE_POLLEN = 1;   // Sample/Pollen tracking pipeline

    // Target constraints for PID calculations
    private final double TURRET_KP = 0.04;
    private final double DISTANCE_KP = 0.05;
    private final double DESIRED_DISTANCE_INCHES = 24.0;

    // Autonomous State Management
    private enum AutonState {
        DRIVE_TO_SUBMERSIBLE,
        TRACK_AND_INTAKE,
        DRIVE_TO_BASKET,
        ALIGN_AND_SCORE,
        PARK,
        DONE
    }
    private AutonState currentState = AutonState.DRIVE_TO_SUBMERSIBLE;
    private ElapsedTime stateTimer = new ElapsedTime();
    private ElapsedTime totalAutonTimer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        // 1. Initialize Pedro Pathing
//        follower = new Follower(hardwareMap);
//        follower.setStartingPose(new Point(0, 0, Math.toRadians(0)));
//
//        // 2. Initialize Limelight 3A
//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        limelight.setPollRateHz(100);
//        limelight.start(); // Pre-initialize to avoid match delays
//        limelight.pipelineSwitch(PIPELINE_APRILTAG); // Starts on AprilTags
//
//        // 3. Initialize Turret and Light Sensor
//        turretMotor = hardwareMap.get(DcMotor.class, "turretMotor");
//        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        lightSensor = hardwareMap.get(DigitalChannel.class, "lightSensor");
//        lightSensor.setMode(DigitalChannel.Mode.INPUT);
//
//        // Define Paths using Pedro Pathing
//        Path scorePath = new Path(new BezierLine(new Point(0, 0), new Point(24, 12)));
//        Path returnPath = new Path(new BezierLine(new Point(24, 12), new Point(48, 0)));
//
//        telemetry.addData("Status", "Initialized. BioBuzz Ready.");
//        telemetry.update();
//
//        waitForStart();
//        if (isStopRequested()) return;
//
//        totalAutonTimer.reset();
//        stateTimer.reset();
//
//        // 30-Second Match Loop
//        while (opModeIsActive() && totalAutonTimer.seconds() < 30.0) {
//            follower.update(); // Keep background odometry and path updates running
//
//            // Read the hardware sensors constantly
//            boolean pieceDetected = !lightSensor.getState(); // Active low beam-break
//            LLResult visionResult = limelight.getLatestResult();
//
//            switch (currentState) {
//                case DRIVE_TO_SUBMERSIBLE:
//                    limelight.pipelineSwitch(PIPELINE_POLLEN); // Switch to pick up game pieces
//                    follower.followPath(scorePath);
//                    currentState = AutonState.TRACK_AND_INTAKE;
//                    stateTimer.reset();
//                    break;
//
//                case TRACK_AND_INTAKE:
//                    // Use vision data dynamically to intake
//                    if (visionResult != null && visionResult.isValid()) {
//                        double tx = visionResult.getTx(); // Horizontal offset
//                        // Simple proportional adjustment to center onto the piece
//                        turretMotor.setPower(tx * TURRET_KP);
//                    }
//
//                    // Break out if light sensor confirms we successfully grabbed a piece
//                    if (pieceDetected || stateTimer.seconds() > 5.0) {
//                        turretMotor.setPower(0);
//                        limelight.pipelineSwitch(PIPELINE_APRILTAG); // Instantly swap back to AprilTags
//                        follower.followPath(returnPath);
//                        currentState = AutonState.DRIVE_TO_BASKET;
//                        stateTimer.reset();
//                    }
//                    break;
//
//                case DRIVE_TO_BASKET:
//                    // Wait for Pedro Pathing to reach destination
//                    if (!follower.isBusy() || stateTimer.seconds() > 6.0) {
//                        currentState = AutonState.ALIGN_AND_SCORE;
//                        stateTimer.reset();
//                    }
//                    break;
//
//                case ALIGN_AND_SCORE:
//                    // Dynamically calculate turret rotation angle & speed relative to AprilTag
//                    if (visionResult != null && visionResult.isValid()) {
//                        double tx = visionResult.getTx(); // Angle deviation
//                        double ty = visionResult.getTy(); // Angle pitch for distance calculations
//
//                        // Distance calculation approximation from pitch angle
//                        double currentDistance = calculateDistance(ty);
//                        double distanceError = DESIRED_DISTANCE_INCHES - currentDistance;
//
//                        // Math calculation for speed adjustment
//                        double turretSpeedOutput = tx * TURRET_KP;
//
//                        // Apply speed adjustments to center target
//                        turretMotor.setPower(turretSpeedOutput);
//
//                        // If aligned close enough to center, simulate scoring mechanism trigger
//                        if (Math.abs(tx) < 1.5 && Math.abs(distanceError) < 1.0) {
//                            turretMotor.setPower(0);
//                            // Insert physical actuator / outtake motor movements here
//                            currentState = AutonState.PARK;
//                            stateTimer.reset();
//                        }
//                    } else {
//                        // Fallback logic: sweep slowly if AprilTag lost
//                        turretMotor.setPower(0.15);
//                    }
//                    break;
//
//                case PARK:
//                    // Drive to safe parking layout
//                    if (stateTimer.seconds() > 2.0) {
//                        currentState = AutonState.DONE;
//                    }
//                    break;
//
//                case DONE:
//                    turretMotor.setPower(0);
//                    break;
//            }
//
//            // Diagnostic Telemetry Feedback Loop
//            telemetry.addData("State", currentState);
//            telemetry.addData("Auton Clock", "%.2f / 30s", totalAutonTimer.seconds());
//            telemetry.addData("Target Visual Found", visionResult != null && visionResult.isValid());
//            telemetry.addData("Light Sensor Object Detected", pieceDetected);
//            telemetry.update();
//        }
//
//        // Emergency cutoff after 30 seconds
//        turretMotor.setPower(0);
    }

    /**
     * Estimates distance to an AprilTag using known camera setup mounting metrics.
     */
    private double calculateDistance(double targetPitchAngleDegrees) {
        double cameraHeightInches = 14.5;
        double tagHeightInches = 52.0;    // Standard high basket/chamber AprilTag targets
        double cameraMountAngleDegrees = 20.0;

        double totalAngleRadians = Math.toRadians(cameraMountAngleDegrees + targetPitchAngleDegrees);
        return (tagHeightInches - cameraHeightInches) / Math.tan(totalAngleRadians);

    }
}
