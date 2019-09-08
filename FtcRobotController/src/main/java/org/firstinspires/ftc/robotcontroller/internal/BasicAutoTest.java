package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import static java.lang.Math.PI;

@Autonomous(name="BasicAutoTest", group="Testing")
public class BasicAutoTest extends BaseAuto {

    public void runOpMode() {

        addTelemetry("status: ", "Initializing");
        setupAuto();

        waitForStart();
        startAuto();
        stopDrive();
        int goldPos = scanMineral(3000);

        telemetry.addData("GoldPos = ", goldPos);
        telemetry.update();
        if (goldPos == -1 || goldPos == -2) {
            //Gold Position Left
            driveInches(1, 1);
            runToPos(getTicks(14.5), -getTicks(14.5), 1);
            driveInches(40, .6);
            runToPos(getTicks(9*3.5), -getTicks(9*3.51), .6);
            driveInches(-18, .6);
            dumpMarker();
        }
        else if (goldPos == 0) {
            //Gold Position Center
            driveInches(-1, -1);
            runToPos(getTicks(3), -getTicks(3), 1);
            driveInches(44, .6);
            runToPos(getTicks(58.8), getTicks(-58.8), -.6); //180 deg turn
            dumpMarker();
        }
        else if (goldPos == 1) {
            //Gold Position Left
            driveInches(1, 1);
            runToPos(-getTicks(3), getTicks(3), 1);
            driveInches(40, .6);
            runToPos(-getTicks(27), getTicks(27), .6);
            driveInches(-16, .6);
            dumpMarker();
        }
    }
}
