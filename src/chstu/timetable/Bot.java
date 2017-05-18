package chstu.timetable;

import chstu.db.DBAdapter;
import chstu.db.Labs;
import chstu.db.LessonTimetable;
import chstu.db.Timetable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class Bot {
    public Bot() {
        currentDate = new Date();
        dataBase = DBAdapter.getInstance();

        checkUserDutyForToday();
    }

    Date currentDate;
    DBAdapter dataBase;
    DateUtil dateUtil = new DateUtil();

    List<Timetable> lessonForPass;
    int inProcess = 0, debt = 2;


    private void checkUserDutyForToday() {
        List<Labs> labsForPassToday = dataBase.getLabsByDay(dateUtil.getCurrentDate());
        lessonForPass = new ArrayList<>();

        if (labsForPassToday.size() == 0) {
            System.out.println("No subject for pass today!");
            return;
        }

        for(Labs lab : labsForPassToday){
            lessonForPass.addAll(dataBase.getLessonsForSubjectInDay(lab.getIdSubject(),lab.getDeadline()));
        }
    }

    public void checkLabsStatys(){
        List<LessonTimetable> endOfLessons = dataBase.getLessonTimetable();

        for(Timetable lesson : lessonForPass){
            if(dateUtil.getCurrentTime() >= dateUtil.convertTimeInMS(endOfLessons.get(lesson.getNumberLesson()).getEndLesson())){
                //if(dataBase.getlabStatus(subject,dateUtil.getCurrentDate()) == inProcess) dataBase.setLabStatus(subject,dateUtil.getCurrentDate(),debt);
            }
        }

        if(dateUtil.isMoreLessonsToday(dataBase)) {
            startBotTimer();
        }
    }

    public void startBotTimer(){
        Timer timer = new Timer();
        BotCronTask botCronTask = new BotCronTask(timer);
        timer.schedule(botCronTask,dateUtil.getTimeToNextLesson(dataBase));
    }
}
