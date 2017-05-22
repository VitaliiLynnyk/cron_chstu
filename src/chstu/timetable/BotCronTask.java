package chstu.timetable;

import java.util.Timer;
import java.util.TimerTask;


public class BotCronTask extends TimerTask {
    public BotCronTask(Timer timer) {
        robot = new Bot();
        this.timer = timer;
    }

    Bot robot;
    Timer timer;

    public void run(){
        robot.startBot();
        timer.cancel();
    }
}
