package org.firstinspires.ftc.teamcode.pedro;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class teleop {
    public void init (){


        DcMotor frontLeft = hardwareMap.get(DcMotor.class,"left_front");
        DcMotor frontRight = hardwareMap.get(DcMotor.class,"right_front");
        DcMotor backLeft = hardwareMap.get(DcMotor.class,"left_back");
        DcMotor backRight = hardwareMap.get(DcMotor.class,"right_back");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }
}
