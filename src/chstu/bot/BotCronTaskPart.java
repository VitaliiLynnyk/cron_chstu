package chstu.bot;

import java.util.Timer;
import java.util.TimerTask;


public class BotCronTaskPart extends TimerTask {
    BotProgressCheckedPart robot;
    Timer timer;

    public BotCronTaskPart(Timer timer) {
        robot = new BotProgressCheckedPart();
        this.timer = timer;
    }

    public void run(){
        robot.startCheckProgress();
        timer.cancel();
    }
}
