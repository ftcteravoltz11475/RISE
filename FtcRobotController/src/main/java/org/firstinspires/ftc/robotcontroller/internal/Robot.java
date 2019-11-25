package org.firstinspires.ftc.robotcontroller.internal;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public abstract class Robot extends LinearOpMode{
    private Servo foundationServo, clawServo;
    private DcMotor rightFrontMotor,leftFrontMotor, rightBackMotor, leftBackMotor, liftMotorLeft, liftMotorRight;
    private int posLift, posWheel;

    public void InitializeHardware(){
        //Hardware initiationlization
        foundationServo = hardwareMap.get(Servo.class, "foundationServo");

        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");

        clawServo = hardwareMap.get(Servo.class, "clawServo");

        liftMotorLeft = hardwareMap.get(DcMotor.class, "liftMotorLeft");
        liftMotorRight = hardwareMap.get(DcMotor.class, "liftMotorRight");

        // Lift initialize
        liftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Wheel initialize
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Turning Brake on for wheels
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Getting current positions
        posLift = liftMotorLeft.getCurrentPosition();
        posWheel = leftBackMotor.getCurrentPosition();  // ???
    }

    private float ScalePower(float power){
        //Scales the power to be power * |power|
        return Math.abs(power) * power;
    }

    public void TankDrive(float power1, float power2){
        //Sets the power for the right to power1 scaled and left power2 scaled
        rightFrontMotor.setPower(ScalePower(power1));
        rightBackMotor.setPower(ScalePower(power1));
        leftFrontMotor.setPower(ScalePower(power2));
        leftBackMotor.setPower(ScalePower(power2));
    }

    public void MecanumDrive(float power1, float power2){
        //Sets the power for the right to power1 scaled and left power2 scaled
        rightFrontMotor.setPower(ScalePower(power1));
        rightBackMotor.setPower(-ScalePower(power1));
        leftFrontMotor.setPower(-ScalePower(power2));
        leftBackMotor.setPower(ScalePower(power2));
    }

    public void TurnClaw(){
        clawServo.setPosition(0.5);
    }

    public void TurnClaw(double angle){
        clawServo.setPosition(angle);
    }

    public void ResetClaw(){
        clawServo.setPosition(0);
    }

    public void Lift(double power){
         liftMotorLeft.setPower(power);
         liftMotorRight.setPower(power);
    }

    public void TurnFoundationServo(){
        foundationServo.setPosition(0.5);
    }

    public void TurnFoundationServo(double angle){
        foundationServo.setPosition(angle);
    }

    public void ResetFoundationServo() {
        foundationServo.setPosition(0);
    }

    public int getPosLift(){
        //Get the position of the motor in the lift, 0 is starting position.
        return posLift - liftMotorLeft.getCurrentPosition();
    }

    public int getPosWheel(){
        return leftBackMotor.getCurrentPosition() - posWheel;
    }
}
