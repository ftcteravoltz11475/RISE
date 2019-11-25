package org.firstinspires.ftc.robotcontroller.internal;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "MasterOpModeTest" , group = "Main Op Modes")
public class MasterOpModeTest extends Robot{

    @Override
    public void runOpMode() throws InterruptedException{

        //Initialize Hardware
        InitializeHardware();

        //Other Variables
        final double SLOWSPEED = .7;
        boolean slowDrive = false;
        float rightDriveY, leftDriveY, rightDriveX, leftDriveX;
        double angle = 0;
        int topPos = 6000;


        waitForStart();

        while (opModeIsActive()) {

            rightDriveY = gamepad1.right_stick_y;
            leftDriveY = gamepad1.left_stick_y;
            leftDriveX = gamepad1.left_stick_x;
            rightDriveX = gamepad1.left_stick_x;

            //right bumper closes the claw and vice versa
            if (gamepad1.right_bumper) {
                TurnClaw(angle);
            }
            if(gamepad1.left_bumper){
                ResetClaw();
            }

            //y lifts and a lowers lift, otherwise stop the lift
            if(gamepad1.y && getPosLift() >= 0) {
                Lift(1);
            }
            else if(gamepad1.a && getPosLift()  <= topPos) {
                Lift(-1);
            }
            else {
                Lift(0);
            }


            //b closes the foundation servo and x opens it.
            if (gamepad1.b == true) {
                TurnFoundationServo(angle);
            }
            if(gamepad1.x){
                ResetFoundationServo();
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
                rightDriveY *= SLOWSPEED;
                leftDriveY *= SLOWSPEED;
                rightDriveX *= SLOWSPEED;
                leftDriveX *= SLOWSPEED;
            }

            if(gamepad1.dpad_right){
                angle += 0.01;
            }
            if(gamepad1.dpad_left){
                angle -= 0.01;
            }

            if(){
                TankDrive(rightDriveY, leftDriveY);
            }




            telemetry.addData("Lift Pos:", getPosLift());
            telemetry.addData("Wheel Pos:", getPosWheel());
            telemetry.addData("Angle",angle);
            telemetry.update();
        }
    }
}
