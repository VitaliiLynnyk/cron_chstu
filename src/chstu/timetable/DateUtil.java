package chstu.timetable;

import chstu.db.DBAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public long getCurrentTime(){
        currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        long currentTimeDate = -1;

        try {
            currentTimeDate = dateFormat.parse(dateFormat.format(currentDate)).getTime();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return currentTimeDate;
    }


    public boolean isMoreLessonsToday(DBAdapter dataBase){
        int indexOfEndLessonArray = dataBase.getNumberLessonsInDay(getCurrentDate())-1;
        ArrayList<Long> endOfLesson = dataBase.getEndOfLessons();

        return !(endOfLesson.contains(indexOfEndLessonArray) && getCurrentTime() >= endOfLesson.get(indexOfEndLessonArray));
    }

    public long getTimeToNextLesson(DBAdapter dataBase){
        long timeToNExtLesson = -1;

        if(isMoreLessonsToday(dataBase)) {
            for (int i = 0; i < dataBase.getEndOfLessons().size(); i++){
                if (getCurrentTime() < dataBase.getEndOfLessons().get(i)){
                    timeToNExtLesson = dataBase.getEndOfLessons().get(i) - getCurrentTime();
                }
            }
        }
        return  timeToNExtLesson;
    }

}
