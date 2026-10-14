package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("front_left");
        c.frontRightName.set("front_right");
        c.backLeftName.set("back_left");
        c.backRightName.set("back_right");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(0.0484192887629111);
        c.yPodOffset.set(-3.2602087906965123);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.17849606933572418);
                Controller secondaryTranslationalForward = Controller.proportional(0.06594952811673624);
                Controller primaryTranslationalLateral = Controller.proportional(0.21572322641720892);
                Controller secondaryTranslationalLateral = Controller.proportional(0.07970396793038748);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.01913672756715024));
                c.brake.set(Controller.proportionalFeedforward(0.016266218432077702));

                c.headingFeedback.set(Controller.proportional(5.778745483528586));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.040167401906414296, 0.022125477732680657));

                c.linearBrakeCoefficients.set(Matrix.diag(0.05469573544878263, 0.046452966823138785));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.002239670890074392, 0.002678249322297805));

                c.maxAchievableForwardVelocity.set(55.15259371632813);
                c.maxAchievableStrafeVelocity.set(46.66423645724748);
                c.naturalForwardDeceleration.set(58.75041806069793);
                c.naturalStrafeDeceleration.set(48.661025478606945);
            }
    );
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return null;
    }
}