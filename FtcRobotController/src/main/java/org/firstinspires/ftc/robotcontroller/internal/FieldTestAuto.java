package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.lang.System;

@Autonomous(name="LeadscrewTestAuto", group="Testing")
public class FieldTestAuto extends BaseAuto {

    public void runOpMode() {
        initializeHardware();
        leadScrew.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leadScrew.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
       long startTime = System.currentTimeMillis();
       leadScrew.setTargetPosition(leadScrew.getCurrentPosition() + (int) (6.5 * ticksPerRev));
       leadScrew.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       leadScrew.setPower(1);
       while (leadScrew.isBusy()) {}
       long runTime = startTime - System.currentTimeMillis();
       telemetry.addData("Runtime: ", runTime);
       telemetry.addData("Pos: ", leadScrew.getCurrentPosition());
       telemetry.update();
    }


}
