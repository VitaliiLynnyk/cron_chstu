package chstu.timetable;

import chstu.db.DBAdapter;
import chstu.db.LessonTimetable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateUtil {
    public DateUtil() {
        dataBase = DBAdapter.getInstance();

        currentDate = new Date();
        lessonTimetables = dataBase.getLessonTimetable();
    }

    private Date currentDate;
    private List<LessonTimetable> lessonTimetables;
    private DBAdapter dataBase;

    public String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(currentDate);
    }

    public long getCurrentTimeMS(){
        currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");
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
            if(getCurrentTimeMS() < convertTimeInMS(endOfLesson.getEndLesson())){
                timeToNExtLesson = convertTimeInMS(endOfLesson.getEndLesson()) - getCurrentTimeMS();
            }
        }

        return  timeToNExtLesson;
    }

}
