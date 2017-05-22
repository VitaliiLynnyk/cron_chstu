package chstu.timetable;

import chstu.db.DBAdapter;
import chstu.db.Labs;
import chstu.db.LessonTimetable;
import chstu.db.Timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class Bot {
    public Bot() {
        currentDate = new Date();
        dataBase = DBAdapter.getInstance();
    }

    Date currentDate;
    DBAdapter dataBase;
    DateUtil dateUtil = new DateUtil();

    List<Labs> labsForPassToday;
    List<Timetable> lessonForPass;
    int inProcess = 0, debt = 2;


    private boolean haveUserDutyForToday() {
        labsForPassToday = dataBase.getLabsByDay(dateUtil.getCurrentDate());
        lessonForPass = new ArrayList<>();

        if (labsForPassToday.size() == 0) {
            System.out.println("No subject for pass today!");
            return false;
        }

        for(Labs lab : labsForPassToday){
            lessonForPass.addAll(dataBase.getLessonsForSubjectInDay(lab.getIdSubject(),lab.getDeadline()));
        }

        return true;
    }

    public void startBot(){
        if (!haveUserDutyForToday()){
            startTimer(dateUtil.getTimeToNextDayLesson());
            return;
        }

        for(Timetable lesson : lessonForPass){
            if(dateUtil.getCurrentTimeMS() >= getLessonTimeInMS(lesson.getNumberLesson())){
                System.out.println(getLessonTimeInMS(lesson.getNumberLesson()));
                for(Labs lab : labsForPassToday){
                    if (lab.getIdSubject() == lesson.getIdSubject() && lab.getStatus() == inProcess) {
                        dataBase.updateLabStatus(debt,lab.getIdSubject(),lab.getLabNumber());
                    }
                }
            }
        }

        if(dateUtil.getTimeToNextLesson() > 0) {
            startTimer(dateUtil.getTimeToNextLesson());
        }
        else{
            startTimer(dateUtil.getTimeToNextDayLesson());
        }
    }

    private long getLessonTimeInMS(int numberLesson){
        long lessonTime = -1;
        List<LessonTimetable> endOfLessons = dataBase.getLessonTimetable();

        for (LessonTimetable timetable : endOfLessons){
            if (timetable.getId() == numberLesson){
                lessonTime = dateUtil.convertTimeInMS(timetable.getEndLesson());
            }
        }

        return lessonTime;
    }

    private void startTimer(long delay){
        Timer timer = new Timer();
        BotCronTask botCronTask = new BotCronTask(timer);
        timer.schedule(botCronTask,delay);
    }
}
