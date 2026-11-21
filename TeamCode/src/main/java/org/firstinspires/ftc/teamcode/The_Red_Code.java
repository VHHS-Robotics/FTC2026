package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class The_Red_Code {
    private DcMotor flyWheel;

    public void init() {
        flyWheel = hardwareMap.get(DcMotor.class, "fly_wheel");

    }
}