package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*

 */
@TeleOp(name="FoundationMover", group="Testing Op Modes")
public class FoundationMover extends LinearOpMode {

    //Declaring the hardware
    Servo foundationServo = hardwareMap.get(Servo.class, "foundationServo");
    DcMotor rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
    DcMotor leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
    DcMotor rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
    DcMotor leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");



    @Override
    public void runOpMode() throws InterruptedException {
        //Variable declarations
        final double SLOWSPEED = .7;
        boolean slowDrive = false;
        float rightDrive, leftDrive;
        boolean turned = false;

        //Declaring the servo

        waitForStart();
        //telemetry.addData("Status:", "Starting FoundationMover");

        while (opModeIsActive()) {

            rightDrive = gamepad1.right_stick_y;
            leftDrive = gamepad1.left_stick_y;

            if (gamepad1.a == true) {
                //Pressing a either turns or resets the position of the servo
                if(!turned) {
                    foundationServo.setPosition(0.5);
                    turned = true;
                    //telemetry.addData("Turning Servo:", "90 degrees");
                }
                else {
                    foundationServo.setPosition(0);
                    turned = false;
                    //telemetry.addData("Turning Servo:", "0 degrees");
                }
            }

            //If the dpad up is pressed, turn off slow drive
            if(gamepad1.dpad_up == true){
                slowDrive = false;
            }
            //If dpad down is pressed, turn on slow drive
            if (gamepad1.dpad_down == true){
                slowDrive = true;
            }

            if (slowDrive == true){
                rightDrive *= SLOWSPEED;
                leftDrive *= SLOWSPEED;
            }
            //If the difference between rightDrive and leftDrive isn't large,
            /*
            if(isSmallEnoughDifference(rightDrive,leftDrive)){
                float max = Math.max(rightDrive,leftDrive);
                drive(max,max);
            }
            else {

             */
            drive(rightDrive,leftDrive);

            //telemetry.addData("right power: ", rightDrive);
            //telemetry.addData("left power: ", leftDrive);
            //telemetry.addData("drive mode: ", slowDrive);
            telemetry.update();
        }

    }
    public boolean isSmallEnoughDifference(float rightDrive, float leftDrive){
        //Checks if the difference between the powers is not large
        if(rightDrive >= 0.99 * leftDrive && rightDrive <= 1.01 * leftDrive)
            return true;
        else
            return false;
    }
    public float scalePower(float power){
        //Scales the power to be power * |power|
        return Math.abs(power) * power;
    }

    public void drive(float power1, float power2){
        //Sets the power for the right to power1 scaled and left power2 scaled
        rightFrontMotor.setPower(scalePower(power1));
        rightBackMotor.setPower(-scalePower(power1));
        leftFrontMotor.setPower(scalePower(power2));
        leftBackMotor.setPower(-scalePower(power2));
    }
}
