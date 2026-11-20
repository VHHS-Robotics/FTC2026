
package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import java.util.List;
/**
 * This file contains a minimal example of an iterative (Non-Linear) "OpMode". An OpMode is a
 * 'program' that runs in either the autonomous or the TeleOp period of an FTC match. The names
 * of OpModes appear on the menu of the FTC Driver Station. When an selection is made from the
 * menu, the corresponding OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */
@Autonomous(name = "LimeLight")
public class LimeLight extends OpMode {
    /* Declare OpMode members. */
    private Limelight3A camera;
    private DcMotor front_left;
    private DcMotor front_right;
    private DcMotor back_left;
    private DcMotor back_right;
    private static final double STEER_P = 0.03;
    private final double TURN_KP = 0.02;
    private final double SPEED = 0.5;

    private double alignmentTolerance = 1.5;



    public enum direction {
        Left,
        Right,
        Forward,
        Back,
        TurnL,
        TurnR
    }

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");


        front_left = hardwareMap.get(DcMotor.class, "front_left");
        front_right = hardwareMap.get(DcMotor.class, "front_right");
        back_left = hardwareMap.get(DcMotor.class, "back_left");
        back_right = hardwareMap.get(DcMotor.class, "back_right");


        front_left.setDirection(DcMotorSimple.Direction.FORWARD);
        front_right.setDirection(DcMotorSimple.Direction.FORWARD);
        back_left.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right.setDirection(DcMotorSimple.Direction.REVERSE);


        camera = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
        camera.pipelineSwitch(0);


        camera.start();

        telemetry.addData(">", "Robot Ready.  Press Play.");



        telemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        LLStatus status = camera.getStatus();
        LLResult result = camera.getLatestResult();

        //if the camera found something valid
        // Access detector results/objects it found
        List<LLResultTypes.DetectorResult> detectedObjects = result.getDetectorResults();

        for (LLResultTypes.DetectorResult obj : detectedObjects) { //look at all the objects detected and find the closest
            telemetry.addData("Detector", "Class: %s, Area: %f", obj.getClassName(), obj.getTargetArea());
        }

        telemetry.update();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {


    }


    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        //get information from the camera
        LLStatus status = camera.getStatus();
        LLResult result = camera.getLatestResult();
        double xOffset = result.getTx();
        double targetArea = result.getTa();

        //print out the status
        telemetry.addData("Name", "%s",
                status.getName());
        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());
        telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());

        //if the camera found something valid
        if (result.isValid()) {
            // Access general information
            Pose3D botpose = result.getBotpose();
            telemetry.addData("tx", result.getTx());
            telemetry.addData("txnc", result.getTxNC());
            telemetry.addData("ty", result.getTy());
            telemetry.addData("tync", result.getTyNC());
            telemetry.addData("Botpose", botpose.toString());


            // Access detector results/objects it found
            List<LLResultTypes.DetectorResult> detectedObjects = result.getDetectorResults();

            LLResultTypes.DetectorResult closestTarget = null;
            double maxArea = 0.0;

            for (LLResultTypes.DetectorResult obj : detectedObjects) { //look at all the objects detected and find the closest
                telemetry.addData("Detector", "Class: %s, Area: %.2f", obj.getClassName(), obj.getTargetArea());

                double area = obj.getTargetArea();
                if (area > maxArea) {
                    maxArea = area;
                    closestTarget = obj;
                }
            }

            //drive towards the closest object
            if (closestTarget != null) {
                double tx = closestTarget.getTargetXDegrees();
                double ta = closestTarget.getTargetArea();
                telemetry.addData("TA: ",ta);
                double turnSpeed = tx * STEER_P;
                if (Math.abs(tx) > 10) { //acceptable angle away from center
                    if (turnSpeed > 0) {
                        drive(0.2, 0.1, direction.Right);

                    } else {
                        drive(0.2, 0.1, direction.Left);
                    }
                } else {
                    //robot is aligned drive towards the pollen
                    //is the target area is less tha 40% too far, then drive forward
                    if (ta < 0.09) {
                        drive(0.2, 0.2, direction.Forward);
                    } else {
                        //close enough to pollen so stop - or perform collection action
                        drive(0.0, 0.2, direction.Forward);
                    }
                }
            }
        } else { //didn't find anything
            telemetry.addData("Limelight", "No data available");
            drive(0.2, 0.2, direction.TurnR);
        }

        telemetry.update();
    }

    /**
     * Drive left for a specific period of time
     * @param maxPower max Power ranging from 0-1
     * @param time to run
     * @param dir direction to drive
     *
     * TIME DOES NOT WORK RIGHT NOW BECAUSE I FORGOT HOW TO RUN ASYNC WITHOUT BUKKIT
     * PLS ADD LINTING :3
     **/
    public void drive(double maxPower, double time, direction dir) {
        ElapsedTime timer = new ElapsedTime();
        timer.reset(); //reset timer back to 0 and start it

        double mili = time * 1000; //convert time parameter to miliseconds

        while (timer.milliseconds() < mili) {

            //actually drive based on dir
            switch (dir) {
                case Forward:
                    front_left.setPower(maxPower);
                    front_right.setPower(maxPower);
                    back_left.setPower(maxPower);
                    back_right.setPower(maxPower);
                    break;
                case Back:
                    front_left.setPower(-maxPower);
                    front_right.setPower(-maxPower);
                    back_left.setPower(-maxPower);
                    back_right.setPower(-maxPower);
                    break;
                case Left:
                    front_left.setPower(-maxPower);
                    front_right.setPower(maxPower);
                    back_left.setPower(maxPower);
                    back_right.setPower(-maxPower);
                    break;
                case Right:
                    front_left.setPower(maxPower);
                    front_right.setPower(-maxPower);
                    back_left.setPower(-maxPower);
                    back_right.setPower(maxPower);
                    break;
                case TurnL:
                    front_left.setPower(-maxPower);
                    front_right.setPower(maxPower);
                    back_left.setPower(-maxPower);
                    back_right.setPower(maxPower);
                    break;
                case TurnR:
                    front_left.setPower(maxPower);
                    front_right.setPower(-maxPower);
                    back_left.setPower(maxPower);
                    back_right.setPower(-maxPower);
                    break;
            }
        }
        front_left.setPower(0);
        front_right.setPower(0);
        back_left.setPower(0);
        back_right.setPower(0);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        camera.stop();
        front_left.setPower(0);
        front_right.setPower(0);
        back_left.setPower(0);
        back_right.setPower(0);
    }
}
