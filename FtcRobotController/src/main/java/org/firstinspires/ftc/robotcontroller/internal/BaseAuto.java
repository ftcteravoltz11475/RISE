package org.firstinspires.ftc.robotcontroller.internal;


import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import java.util.List;

import static java.lang.Math.abs;

public abstract class BaseAuto extends BaseOpMode {

    public final String vuforiaKey = "AUAchNn/////AAAAGfqAcfY2+0TviBOpWNWvbFVO+Ki3ke54hx4bK3LAyME" +
            "OoMpSZ8pC6zWh9BQwmaUwpR8FxMbNylft5qxYuRVSaA5ijKZj2Gd5F4m8TKzk9YD+ZTRH0T/bzvhZLMr1IEn" +
            "UKN0wyLqGqQqvI05qNqNahVd9OAHgy+MnrcWfrF1Ta1GUzQGc18K2qC7mioQFIJhc/KMCaFhmOer2sjtmxIp" +
            "/kak0iDJfp77f/8kWvyV2IlnlR187HHWg1mgF9ZZspTYArFZa150FozF7PF7cR9xOuQZT7LuiwO/Ia64M/qa" +
            "4vcOTlcHVtz6CVVC54KW1AAhQEg3p5kkG1hGbHJtvGovp7PKfragvZascLTnkCt4XK28C";

    public VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;

    //For Driving Functions
    double wheelDiameter = 4.0;
    int ticksPerRev = 1120;
    boolean isDriving = false;
    long degree = getTicks(3.5) / 15;

    //for tfod
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    public void setupAuto() {
        initializeHardware();
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            telemetry.update();
        }
    }

    public void runToPos(int rticks, int lticks, double power) {
        double lpower, rpower;
        telemetry.addLine("Run To Pos:");
        telemetry.addData("Left Ticks:", lticks);
        telemetry.addData("Right Ticks:", rticks);
        power = abs(power);
        telemetry.addData("Power", power);
        telemetry.update();
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Left Current Pos:", leftFrontMotor.getCurrentPosition());
        telemetry.addData("Right Current Pos", rightFrontMotor.getCurrentPosition());
        telemetry.update();
        int leftTarget = leftFrontMotor.getCurrentPosition() + lticks;
        int rightTarget = rightFrontMotor.getCurrentPosition() + rticks;

        if (rticks < 0)
            rpower = -power;
        else
            rpower = power;
        if (lticks < 0)
            lpower = -power;
        else
            lpower = power;
        telemetry.addData("Left Target:", leftTarget);
        telemetry.addData("Right Target:", rightTarget);
        telemetry.addData("Power:", power);
        telemetry.update();

        rightFrontMotor.setPower(rpower);
        rightBackMotor.setPower(rpower);
        leftFrontMotor.setPower(lpower);
        leftBackMotor.setPower(lpower);

        while (abs(rightFrontMotor.getCurrentPosition()) < abs(rightTarget) ||
                abs(leftFrontMotor.getCurrentPosition()) < abs(leftTarget)) {
            telemetry.addData("Left Current Pos:", leftFrontMotor.getCurrentPosition());
            telemetry.addData("Right Current Pos:", rightFrontMotor.getCurrentPosition());
            telemetry.update();
            if (!(abs(rightFrontMotor.getCurrentPosition()) < abs(rightTarget))) {
                rightFrontMotor.setPower(0);
                rightBackMotor.setPower(0);
            } else if (!(abs(leftFrontMotor.getCurrentPosition()) < abs(leftTarget))) {
                leftFrontMotor.setPower(0);
                leftBackMotor.setPower(0);
            }
            sleep(10);
        }
        stopDrive();
    }

    private void driveTicks(int ticks, double power) {
        telemetry.addData("Ticks", ticks);
        telemetry.addData("Power", power);
        telemetry.update();
        runToPos(ticks, ticks, power);
    }

    public int getTicks(double inches) {
        double rotations;
        int ticks;
        rotations = inches / (wheelDiameter * Math.PI);
        ticks = (int) (rotations * ticksPerRev);
        return ticks;
    }

    public void driveInches(double inches, double power) {
        int ticks = getTicks(inches);
        driveTicks(ticks, power);
    }

    public void turn(double degrees, double power) {
        //TODO
    }

    public void stopDrive() {
        rightBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = vuforiaKey;
        parameters.cameraDirection = CameraDirection.BACK;
        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        telemetry.addData("Vuforia initalized:", vuforia!=null);
        telemetry.update();
        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        telemetry.addData("TFOD==null:", tfod==null);
        telemetry.update();
    }

    public void extendLeadScrew() {
        leadScrew.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leadScrew.setTargetPosition((int) (6.5 * ticksPerRev)); //DO NOT CHANGE VALUE
        leadScrew.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leadScrew.setPower(1);
        while (leadScrew.isBusy()) {}
        leadScrew.setPower(0);
    }

    public void startAuto() {
        tfod.activate();
        extendLeadScrew();
        driveInches(-2.00, 0.3);
        runToPos(getTicks(-3.5), getTicks(3.5), .6);
        driveInches(-2, .3);
        runToPos(getTicks(25), getTicks(-25), .6); //29.5 is center
        driveInches(-1, .6);
    }

    public void dumpMarker() {
        teamMarker.setPosition(0);
        sleep(750);
        teamMarker.setPosition(1);
    }

    public int scanMineral(long waitTime) {
        /*
        *California Proposition 65 Warning:
        *This code is known to the State of California to cause Cancer, Birth Defects,
        *and other reproductive harm.
        */
        List<Recognition> updatedRecognitions;
        double angle = 0;
        short goldPos = -2;
        int goldMineralX = -1;
        int silverMineral1X = -1;
        int silverMineral2X = -1;
        int start= 0;
        while (start < waitTime) {
            updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                if (updatedRecognitions.size() == 3) {
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                        } else {
                            silverMineral2X = (int) recognition.getLeft();
                        }
                    }
                    if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Left");
                            goldPos = -1;
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
                            goldPos = 0;
                        } else {
                            telemetry.addData("Gold Mineral Position", "Center");
                            goldPos = 1;
                        }
                    }
                    telemetry.update();
                }
                else if (updatedRecognitions.size() == 2) {
                    for (Recognition recognition: updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                        }
                        else {
                            if (silverMineral1X == -1)
                                silverMineral1X = (int) recognition.getLeft();
                            else
                                silverMineral2X = (int) recognition.getLeft();
                        }
                    }
                    if (goldMineralX != -1) {
                        if (goldMineralX < silverMineral1X) {
                            telemetry.addData("Gold Mineral Position", "Center");
                            telemetry.update();
                            goldPos = 0;
                        }
                        else {
                            telemetry.addData("Gold Mineral Position", "Right");
                            telemetry.update();
                            goldPos = 1;
                        }
                    }
                    else {
                        telemetry.addData("Gold Mineral Position", "Left");
                        telemetry.update();
                        goldPos = -1;
                    }
                }
            }
            sleep(100);
            start += 100;
        }
        return goldPos;
    }
}
