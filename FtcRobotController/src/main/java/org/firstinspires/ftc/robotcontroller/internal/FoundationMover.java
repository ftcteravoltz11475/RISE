package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/*

 */
@TeleOp(name="FoundationMover", group="Testing Op Modes")
public class FoundationMover extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        //Declaring the servo
        Servo servo = hardwareMap.get(Servo.class, "Servo");

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
                servo.setPosition(1);
                telemetry.addData("Resetting Servo:", "0 degrees");
            }
            telemetry.update();
        }

    }
}
