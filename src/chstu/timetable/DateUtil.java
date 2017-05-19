package chstu.timetable;

import chstu.db.DBAdapter;
import chstu.db.LessonTimetable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ar-Krav on 14.05.2017.
 */
public class DateUtil {
    public DateUtil() {
        dataBase = DBAdapter.getInstance();

        currentDate = new Date();
        lessonTimetables = dataBase.getLessonTimetable();
    }

    Date currentDate;
    List<LessonTimetable> lessonTimetables;
    DBAdapter dataBase;

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

    public long convertTimeInMS(String time){
        long miliseconds = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        try {
            miliseconds = dateFormat.parse(time).getTime();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Convert time impossible");
        }

        return miliseconds;
    }

    public long getTimeToNextLesson(){
        long timeToNExtLesson = -1; //In position where method called, should be verification about not -1. It`s mean an error statement!

        for(LessonTimetable endOfLesson : lessonTimetables){
            if(getCurrentTime() < convertTimeInMS(endOfLesson.getEndLesson())){
                timeToNExtLesson = convertTimeInMS(endOfLesson.getEndLesson()) - getCurrentTime();
            }
        }

        return  timeToNExtLesson;
    }

}
