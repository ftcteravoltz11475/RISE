package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="ArmTest", group="Testing")
public class ArmTest extends BaseOpMode {

    DcMotor armMotor;
    CRServo collector;

    public void runOpMode() {
        //Initialize hardware
        addTelemetry("Status", "Initializing");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        collector = hardwareMap.get(CRServo.class, "collector");
        collector.setDirection(DcMotorSimple.Direction.REVERSE);
        boolean collectorUp = false;
        boolean collectorDown = false;
        int collectorUpDelay =  0;
        int collectorDownDelay = 0;
        addTelemetry("Status:", "Waiting for Start");
        waitForStart();
        while (opModeIsActive()) {
            double motorPower =  0.3;
            double servoPower =  1;
            //Arm Control
            if (gamepad1.right_bumper)
                armMotor.setPower(motorPower);
            else if (gamepad1.left_bumper)
                armMotor.setPower(-motorPower);
            else
                armMotor.setPower(0);

            //Collector control
            if (gamepad2.dpad_up) {
                if (collectorDown || (!collectorUp && collectorUpDelay == 0)) {
                    collectorUp = true;
                    collectorDown = false;
                    collectorUpDelay = 150;
                    collectorDownDelay = 0;
                    collector.setPower(servoPower);
                }
                else if (collectorUp && collectorUpDelay == 0) {
                    collectorUpDelay = 150;
                    collectorUp = false;
                    collector.setPower(0);
                }
            }
            else if (gamepad2.dpad_down) {
                if (collectorUp || (!collectorDown && collectorDownDelay == 0)) {
                    collectorDown = true;
                    collectorUp = false;
                    collectorDownDelay = 150;
                    collectorUpDelay = 0;
                    collector.setPower(-servoPower);
                }
                else if (collectorDown && collectorDownDelay == 0) {
                    collectorDown = false;
                    collectorDownDelay = 150;
                    collector.setPower(0);
                }
            }

            if (collectorUpDelay > 0)
                collectorUpDelay -= 10;
            if (collectorDownDelay > 0)
                collectorDownDelay -= 10;
            sleep(10);
        }


    }

}
