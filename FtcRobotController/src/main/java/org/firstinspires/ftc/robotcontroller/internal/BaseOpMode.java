package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class BaseOpMode extends LinearOpMode
{

    //Declare Hardware
    DcMotor mineralLifter;
    DcMotor clawLift;
    DcMotor leftFrontMotor;
    DcMotor leftBackMotor;
    DcMotor rightFrontMotor;
    DcMotor rightBackMotor;
    DcMotor leadScrew;
    DcMotor flipper;
    Servo collector;
    Servo teamMarker;

    /*Adjusts drive power by simply squaring the initial power. Squaring is close enough to the
      Desired exponential shift & a lot clearer, not to mention less work */
    double adjustPower(double power) {
        if (power < 0)
            return -(power * power);
        else
            return power * power;

    }

    double getRight() {
        return -gamepad1.right_stick_y;
    }
    double getLeft() {
        return -gamepad1.left_stick_y;
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

    double scalePower(double power, double max) {
        /*Scales power to be between 0 and max*/
        if (max > 1) {
            throw new IllegalArgumentException("Maximum Power cannot be > 1");
        }
        return max * power;
    }

    void initializeHardware () {
        //Initialize Hardware
        addTelemetry("Status: ", "Initializing Hardware");
        clawLift = hardwareMap.get(DcMotor.class, "clawLift");
        mineralLifter = hardwareMap.get(DcMotor.class, "mineralLifter");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        leadScrew = hardwareMap.get(DcMotor.class, "leadScrew");
        flipper = hardwareMap.get(DcMotor.class, "flipper");
        collector = hardwareMap.get(Servo.class, "collector");
        teamMarker = hardwareMap.get(Servo.class, "teamMarker");
        addTelemetry("Hardware: ", "Initalized");


        //Correct motor & CR Servo directions
        mineralLifter.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        clawLift.setDirection(DcMotorSimple.Direction.REVERSE);

        //Set Servo Positions
        teamMarker.setPosition(1);

        //Set motor runmodes
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leadScrew.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        addTelemetry("Hardware Setup: ", "Complete");
    }

}
