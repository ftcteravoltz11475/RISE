package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name="TwoMotors", group="Competition Op Modes")
public class TwoMotorOpMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        DcMotor leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        waitForStart();
        addTelemetry("Status:", "Starting TwoMotorOpMode");
        while (opModeIsActive()) {
          if (gamepad1.a) {
              addTelemetry("A button:", "Active");

          }
          float rightdrive = gamepad1.right_stick_y;
          float leftdrive = gamepad1.left_stick_y;

          rightMotor.setPower(rightdrive);
          leftMotor.setPower(leftdrive);
          addTelemetry("right power: ", rightdrive);
          addTelemetry("left power: ", leftdrive);
        }

    }

    void addTelemetry(String caption, String value, int... times) {
        if (times.length > 0) {
            for (int n : times) {
                while (n-- > 0)
                    telemetry.addData(caption, value);
            }
        }
        else
            telemetry.addData(caption, value);
        telemetry.update();
    }
    void addTelemetry(String caption, float value){
        telemetry.addData(caption, value);
    }
}
