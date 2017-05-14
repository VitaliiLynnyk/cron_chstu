package chstu.timetable;

import chstu.db.DBAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ar-Krav on 14.05.2017.
 */
public class DateUtil {
    public DateUtil() {
        currentDate = new Date();
    }

    Date currentDate;

    public String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }

    public Date getCurrentTime(){
        currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date currentTimeDate = null;

        try {
            currentTimeDate = dateFormat.parse(dateFormat.format(currentDate));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return currentTimeDate;
    }


    public boolean isMoreLessonsToday(DBAdapter dataBase){
        if(getCurrentTime().getTime() >= dataBase.getEndOfLessons().get(dataBase.getNumberLessonsInDay(getCurrentDate())-1).getTime()) return false;
        else return true;
    }

    public long getTimeToNextLesson(DBAdapter dataBase){
        long timeToNExtLesson = -1;

        if(isMoreLessonsToday(dataBase)) {
            for (int i = 0; i < dataBase.getEndOfLessons().size(); i++){
                if (getCurrentTime().getTime() < dataBase.getEndOfLessons().get(i).getTime()){
                    timeToNExtLesson = dataBase.getEndOfLessons().get(i).getTime() - getCurrentTime().getTime();
                }
            }
        }
        return  timeToNExtLesson;
    }

}
