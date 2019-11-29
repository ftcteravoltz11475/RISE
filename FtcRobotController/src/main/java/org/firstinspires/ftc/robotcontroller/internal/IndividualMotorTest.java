package org.firstinspires.ftc.robotcontroller.internal;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "IndividualMotorTest", group ="Testing Op Modes")
public class IndividualMotorTest extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException{
        DcMotor rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        DcMotor leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        DcMotor rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        DcMotor leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");

        waitForStart();

        while(opModeIsActive()) {

            if (gamepad2.a) {
                rightBackMotor.setPower(1);
            }

            if (gamepad2.b) {
                rightFrontMotor.setPower(1);
            }

            if (gamepad2.y) {
                leftBackMotor.setPower(1);
            }

            if (gamepad2.x) {
                leftFrontMotor.setPower(1);
            }
            else{
                rightFrontMotor.setPower(0);
                leftFrontMotor.setPower(0);
                rightBackMotor.setPower(0);
                leftBackMotor.setPower(0);
            }
        }

    }

}
