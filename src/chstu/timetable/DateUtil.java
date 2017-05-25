package chstu.timetable;

import chstu.db.DBAdapter;
import chstu.db.LessonTimetable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        long timeToNextLesson = -1; //In position where method called, should be verification about not -1. It`s mean an error statement!
        int maxLessonInDay = 0;

        for(LessonTimetable endOfLesson : lessonTimetables){
            if(getCurrentTimeMS() < convertTimeInMS(endOfLesson.getEndLesson()) && maxLessonInDay < dataBase.getLessonsInDay(getCurrentDate()).size()){
                timeToNextLesson = convertTimeInMS(endOfLesson.getEndLesson()) - getCurrentTimeMS();
            }
        }

        return  timeToNextLesson;
    }

    public long getTimeToNextDayLesson(){
        return convertTimeInMS("23:59:59") - getCurrentTimeMS() + convertTimeInMS("08:30:01");
    }

    public GregorianCalendar convertStringInDate(String dateToConvert){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        GregorianCalendar calendar = new GregorianCalendar();
        try {
            Date date = sdf.parse(dateToConvert);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

}
