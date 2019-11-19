package org.firstinspires.ftc.robotcontroller.internal;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "AutonomousMode" , group = "Main Op Modes")
public class AutonomousMode extends LinearOpMode{

    Robot robot;

    @Override
    public void runOpMode() throws InterruptedException{
        robot = new Robot();

    }
}
