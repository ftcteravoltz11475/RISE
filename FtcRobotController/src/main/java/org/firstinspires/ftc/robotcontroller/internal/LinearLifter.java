package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Linear Lifter", group = "Testing Op Modes")
public class LinearLifter extends LinearOpMode{

    DcMotor leftLift = hardwareMap.get(DcMotor.class, "leftLift");
    DcMotor rightLift = hardwareMap.get(DcMotor.class, "rightLift");

    @Override
    public void runOpMode() throws InterruptedException {
        float power = 0;
        //boolean up = false;

        waitForStart();

        while(opModeIsActive()){
            wait(500);
            if(gamepad1.dpad_down){
                power -= 0.1;
            }
            if(gamepad1.dpad_up){
                power += 0.1;
            }
            if(gamepad1.dpad_right){
                power += 0.01;
            }
            if(gamepad1.dpad_left){
                power -= 0.01;
            }

            if(gamepad1.b){
                leftLift.setPower(power);
                rightLift.setPower(-power);
            }

            telemetry.addData("Power: ", power);
            telemetry.update();

        }
    }
}
