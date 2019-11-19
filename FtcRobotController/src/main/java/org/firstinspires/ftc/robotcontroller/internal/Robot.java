package org.firstinspires.ftc.robotcontroller.internal;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Robot{
    private Servo foundationServo, clawServo;
    private DcMotor rightFrontMotor,leftFrontMotor, rightBackMotor, leftBackMotor, liftMotorLeft, liftMotorRight;
    private boolean lift = true, clawTurned = false, foundationTurned = false, lower = false;
    private boolean clawButtonPressed = false, foundationButtonPressed = false, liftButtonPressed = false;
    private int iter = 0;
    public Robot(){
        HardwareMap hardwareMap = null;
        foundationServo = hardwareMap.get(Servo.class, "foundationServo");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        liftMotorLeft = hardwareMap.get(DcMotor.class, "liftMotorLeft");
        liftMotorRight = hardwareMap.get(DcMotor.class, "liftMotorRight");
        liftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public float ScalePower(float power){
        //Scales the power to be power * |power|
        return Math.abs(power) * power;
    }

    public void Drive(float power1, float power2){
        //Sets the power for the right to power1 scaled and left power2 scaled
        rightFrontMotor.setPower(ScalePower(power1));
        rightBackMotor.setPower(ScalePower(power1));
        leftFrontMotor.setPower(ScalePower(power2));
        leftBackMotor.setPower(ScalePower(power2));
    }

    public void TurnClawServo(boolean buttonPressed){
        boolean clicked = false;
        if(buttonPressed){
            clawButtonPressed = true;
        }
        else if(!buttonPressed && clawButtonPressed){
            clawButtonPressed = false;
            clicked =  true;
        }
        if(clicked && !clawTurned) {
            clawServo.setPosition(0.5);
            clawTurned = true;
        }
        if(clicked && clawTurned){
            clawServo.setPosition(0);
            clawTurned = false;
        }
    }

    public void TurnFoundationServo(boolean buttonPressed){
        boolean clicked = false;
        if(buttonPressed){
            foundationButtonPressed = true;
        }
        else if(!buttonPressed && clawButtonPressed){
            foundationButtonPressed = false;
            clicked =  true;
        }
        if (clicked && !foundationTurned) {
            foundationServo.setPosition(0.5);
            foundationTurned = true;
        }
        if(clicked && foundationTurned){
            foundationServo.setPosition(0);
            foundationTurned = false;
        }
    }

    public void Lift(boolean buttonPressed){
        boolean clicked = false;
        if(buttonPressed){
            liftButtonPressed = true;
        }
        else if(!buttonPressed && clawButtonPressed){
            liftButtonPressed = false;
            clicked =  true;
        }
        if(clicked == true && lift == true) {
            liftMotorLeft.setPower(1);
            liftMotorRight.setPower(-1);
            lift = false;
            iter++;
        }
        if(clicked == true && lower == true){
            liftMotorLeft.setPower(-1);
            liftMotorRight.setPower(1);
            lower = false;
            iter++;
        }
        else if(!lower&&!lift){
            liftMotorLeft.setPower(0);
            liftMotorRight.setPower(0);
            iter++;
            if(iter/2%2 == 1){
                lower = true;
            }
            else{
                lift = true;
            }
        }
    }
}
