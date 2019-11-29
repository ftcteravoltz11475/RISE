package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="motorTest", group="Testing")
public class motorTest extends BaseOpMode {

    DcMotor tester;


    public void runOpMode() {
        //Initialize hardware
        addTelemetry("Status", "Initializing");
        tester = hardwareMap.get(DcMotor.class, "tester");
        double power = 0;
        addTelemetry("Status:", "Waiting for Start");
        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.a && power<1) {
                power+=0.05;
            }
            else if(gamepad1.b && power>-1) {
                power-=0.05;
            }
            if(gamepad1.x) {
                power = 0.6;
            }
            if(gamepad1.y) {
                power = -0.6;
            }
            telemetry.addData("Power: ", power);
            telemetry.update();


            if(gamepad1.left_bumper) {
                tester.setPower(power);
            }
            else
                tester.setPower(0);



            sleep(100);
        }


    }

}
