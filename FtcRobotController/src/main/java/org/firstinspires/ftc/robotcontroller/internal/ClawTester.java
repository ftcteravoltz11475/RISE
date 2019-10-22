package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*

 */
@TeleOp(name="ClawTester", group="Testing Op Modes")
public class ClawTester extends LinearOpMode {

    //Declaring the hardware
    Servo clawServo = hardwareMap.get(Servo.class, "clawServo");
    DcMotor liftMotorLeft = hardwareMap.get(DcMotor.class, "liftMotorLeft");
    DcMotor liftMotorRight = hardwareMap.get(DcMotor.class, "liftMotorRight");

    @Override
    public void runOpMode() throws InterruptedException {
        //Variable declarations
        boolean turned = false;
        float power = 0;

        waitForStart();
        //telemetry.addData("Status:", "Starting FoundationMover");

        while (opModeIsActive()) {

            if (gamepad1.a == true) {
                //Pressing a either turns or resets the position of the servo
                if(!turned) {
                    clawServo.setPosition(0.5);
                    turned = true;
                    //telemetry.addData("Turning Servo:", "90 degrees");
                }
                else {
                    clawServo.setPosition(0);
                    turned = false;
                    //telemetry.addData("Turning Servo:", "0 degrees");
                }
            }
            if(gamepad1.dpad_up){
                power += 0.1;
            }

            if(gamepad1.dpad_down){
                power -= 0.1;
            }
            
            liftMotorLeft.setPower(power);
            liftMotorRight.setPower(-power);


            telemetry.update();
        }

    }
}
