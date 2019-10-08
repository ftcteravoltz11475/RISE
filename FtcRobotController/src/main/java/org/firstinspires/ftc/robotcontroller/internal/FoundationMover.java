package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*

 */
@TeleOp(name="FoundationMover", group="Testing Op Modes")
public class FoundationMover extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        double slowspeed = .7;
        boolean slowDrive = false;
        float rightDrive, leftDrive;

        //Declaring the servo
        Servo servo = hardwareMap.get(Servo.class, "Servo");
        DcMotor rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        DcMotor leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        DcMotor rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        DcMotor leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");

        waitForStart();
        telemetry.addData("Status:", "Starting FoundationMover");

        while (opModeIsActive()) {
            if (gamepad1.a == true) {
                //Turns the servo 90 degrees
                servo.setPosition(0.5);
                telemetry.addData("Turning Servo:",  "90 degrees");
            }

            if(gamepad1.b == true){
                //Resets the servo
                servo.setPosition(0);
                telemetry.addData("Resetting Servo:", "0 degrees");
            }
            rightDrive = gamepad1.right_stick_y;
            leftDrive = gamepad1.left_stick_y;

            if(gamepad1.dpad_up == true){
                slowDrive = false;
            }

            if (gamepad1.dpad_down == true){
                slowDrive = true;
            }

            if (slowDrive == true){
                rightDrive *= slowspeed;
                leftDrive *= slowspeed;
            }

            rightFrontMotor.setPower(-Math.abs(rightDrive)*rightDrive);
            rightBackMotor.setPower(Math.abs(rightDrive)*rightDrive);
            leftFrontMotor.setPower(-Math.abs(leftDrive)*leftDrive);
            leftBackMotor.setPower(Math.abs(leftDrive)*leftDrive);

            telemetry.addData("right power: ", rightDrive);
            telemetry.addData("left power: ", leftDrive);
            telemetry.addData("drive mode: ", slowDrive);
            telemetry.update();
        }

    }
}
