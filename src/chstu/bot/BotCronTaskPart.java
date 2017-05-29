package chstu.bot;

import java.util.Timer;
import java.util.TimerTask;


public class BotCronTaskPart extends TimerTask {
    public BotCronTaskPart(Timer timer) {
        robot = new BotProgressCheckedPart();
        this.timer = timer;
    }

    BotProgressCheckedPart robot;
    Timer timer;

    public void run(){
        robot.startCheckProgress();
        timer.cancel();
    }
}
