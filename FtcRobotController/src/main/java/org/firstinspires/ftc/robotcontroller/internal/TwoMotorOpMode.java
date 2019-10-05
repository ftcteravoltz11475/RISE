package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name="TwoMotors", group="Competition Op Modes")
public class TwoMotorOpMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        double slowspeed = .2;
        boolean slowdrive = false;
        DcMotor rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        DcMotor leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        DcMotor rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        DcMotor leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
        waitForStart();
        addTelemetry("Status:", "Starting TwoMotorOpMode");
        while (opModeIsActive()) {
          if (gamepad1.a) {
              addTelemetry("A button:", "Active");

          }
          float rightdrive = gamepad1.right_stick_y;
          float leftdrive = gamepad1.left_stick_y;

          if(gamepad1.dpad_up == true){
              slowdrive = false;
          }
          if (gamepad1.dpad_down == true){
              slowdrive = true;
          }

          if (slowdrive == true){
              rightdrive *= slowspeed;
              leftdrive *= slowspeed;
          }
            rightFrontMotor.setPower(rightdrive);
            rightBackMotor.setPower(-rightdrive);
            leftFrontMotor.setPower(leftdrive);
            leftBackMotor.setPower(-leftdrive);
          addTelemetry("right power: ", rightdrive);
          addTelemetry("left power: ", leftdrive);
          telemetry.addData("drive mode: ", slowdrive);
          telemetry.update();
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
