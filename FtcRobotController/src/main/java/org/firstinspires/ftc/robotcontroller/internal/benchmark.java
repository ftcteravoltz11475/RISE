package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="benchmark", group="Testing")
public class benchmark extends BaseOpMode {
    DcMotor tester;
    public void runOpMode() {
        //Initialize hardware
        addTelemetry("Status", "Initializing");
        tester = hardwareMap.get(DcMotor.class, "tester");
        double power = 0;
        addTelemetry("Status:", "Waiting for Start");
        waitForStart();

        while (opModeIsActive()) {
            power = testPower(power);
            sleep(100);
        }
    }

    public double testPower(double power){
        if (gamepad1.dpad_up) {
            power += 0.1;
        }
        if (gamepad1.dpad_down) {
            power -= 0.1;
        }
        if (gamepad1.dpad_right) {
            power += 0.01;
        }
        if (gamepad1.dpad_left) {
            power -= 0.01;
        }
        telemetry.addData("Power", power);
        telemetry.update();

        if (gamepad1.left_bumper) {
            tester.setPower(0);
            power = 0;
        } else {
            tester.setPower(power);
        }
        return power;
    }
}
