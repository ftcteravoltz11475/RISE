package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.*;
/*

 */
@TeleOp(name="ClawTester", group="Testing Op Modes")
public class ClawTester extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        //Declaring the hardware
        Servo clawServo = hardwareMap.get(Servo.class, "clawServo");
        DcMotor liftMotorLeft = hardwareMap.get(DcMotor.class, "liftMotorLeft");
        DcMotor liftMotorRight = hardwareMap.get(DcMotor.class, "liftMotorRight");

        //Variable declarations
        boolean turned = false;
        float power = 0;
        liftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        //telemetry.addData("Status:", "Starting FoundationMover");

        while (opModeIsActive()) {

            if (gamepad1.right_bumper) {
                clawServo.setPosition(0.5);
            }
            if(gamepad1.left_bumper){
                clawServo.setPosition(0);
            }
            if(gamepad1.dpad_up){
                power += 0.1;
            }

            if(gamepad1.dpad_down){
                power -= 0.1;
            }

            power = Range.clip(power, 0, 1);

            if(gamepad1.y) {
                liftMotorLeft.setPower(power);
                liftMotorRight.setPower(-power);
            }
            else if(gamepad1.a) {
                liftMotorLeft.setPower(-power);
                liftMotorRight.setPower(power);
            }
            else {
                liftMotorLeft.setPower(0);
                liftMotorRight.setPower(0);
            }
            telemetry.addData("Power: " , power);
            sleep(40);
            telemetry.update();
        }

    }
}
