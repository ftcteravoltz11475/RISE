package org.firstinspires.ftc.robotcontroller.internal;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "MasterOpMode" , group = "Main Op Modes")
public class MasterOpMode extends LinearOpMode{

    //Declaring the hardware
    Servo foundationServo;
    DcMotor rightFrontMotor;
    DcMotor leftFrontMotor;
    DcMotor rightBackMotor;
    DcMotor leftBackMotor;
    Servo clawServo;
    DcMotor liftMotorLeft;
    DcMotor liftMotorRight;

    @Override
    public void runOpMode() throws InterruptedException{
        //Initializing the hardware
        foundationServo = hardwareMap.get(Servo.class, "foundationServo");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        liftMotorLeft = hardwareMap.get(DcMotor.class, "liftMotorLeft");
        liftMotorRight = hardwareMap.get(DcMotor.class, "liftMotorRight");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        liftMotorLeft = hardwareMap.get(DcMotor.class, "liftMotorLeft");
        liftMotorRight = hardwareMap.get(DcMotor.class, "liftMotorRight");

        //Variable declarations
        final double SLOWSPEED = .7;
        boolean slowDrive = false;
        float rightDrive, leftDrive;
        boolean turnedClawServo = false;
        boolean turnedFoundationServo = false;
        boolean liftStop = true;
        boolean liftToggleLift = true;
        boolean liftToggleLower = false;
        float power = 0;
        liftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        //telemetry.addData("Status:", "Starting FoundationMover");

        while (opModeIsActive()) {

            rightDrive = gamepad1.right_stick_y;
            leftDrive = gamepad1.left_stick_y;

            if (gamepad1.right_bumper) {
                 clawServo.setPosition(0.5);
            }
            if(gamepad1.left_bumper){
                clawServo.setPosition(0);
            }
            power = Range.clip(power, 0, 1);

            if(gamepad1.y) {
                liftMotorLeft.setPower(power);
                liftMotorRight.setPower(-power);
            }
            else if(gamepad1.a) {
                liftMotorLeft.setPower(-power);
                liftMotorRight.setPower(power);
            }
            else {
                liftMotorLeft.setPower(0);
                liftMotorRight.setPower(0);
            }
            telemetry.addData("Power: " , power);
            sleep(40);
            telemetry.update();

            if (gamepad1.b == true) {
                foundationServo.setPosition(0.5);
            }
            if(gamepad1.x){
                foundationServo.setPosition(0);
            }

            //If the dpad up is pressed, turn off slow drive
            if (gamepad1.dpad_up == true) {
                slowDrive = false;
            }
            //If dpad down is pressed, turn on slow drive
            if (gamepad1.dpad_down == true) {
                slowDrive = true;
            }

            if (slowDrive == true) {
                rightDrive *= SLOWSPEED;
                leftDrive *= SLOWSPEED;
            }
            Drive(rightDrive, leftDrive);

            telemetry.update();
        }
    }

    public float ScalePower(float power){
        //Scales the power to be power * |power|
        return Math.abs(power) * power;
    }

    public void Drive(float power1, float power2){
        //Sets the power for the right to power1 scaled and left power2 scaled
        rightFrontMotor.setPower(ScalePower(power1));
        rightBackMotor.setPower(-ScalePower(power1));
        leftFrontMotor.setPower(ScalePower(power2));
        leftBackMotor.setPower(-ScalePower(power2));
    }
}
