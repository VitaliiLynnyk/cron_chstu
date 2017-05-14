package chstu.timetable;

import chstu.db.DBAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;


public class BotCronTask extends TimerTask {
    public BotCronTask(String currentTime, DBAdapter dataBase) {
        this.dataBase = dataBase;
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        try{
            this.currentTime = dateFormat.parse(currentTime);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Can`t get current time");
        }
    }

    Date currentTime;
    DBAdapter dataBase;

    public void run(){
        ArrayList<Date> endOfLessons = dataBase.getEndOfLessons();
        for (int i = 0; i < endOfLessons.size(); i++){
            if (currentTime.getTime() >= endOfLessons.get(i).getTime()){

            }
        }
    }
}
