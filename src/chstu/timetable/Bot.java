package chstu.timetable;

import chstu.db.DBAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class Bot {
    public Bot() {
        currentDate = new Date();
        dataBase = new DBAdapter();

        checkUserDutyForToday();
    }

    Date currentDate;
    DBAdapter dataBase;
    DateUtil dateUtil = new DateUtil();

    ArrayList<Integer> subjectsForPassToday;
    ArrayList<Integer> numberLessonsOfSubjectForPass;
    int inProcess = 0, debt = 2;


    private void checkUserDutyForToday() {
        subjectsForPassToday = dataBase.getSubjectsForPass(dateUtil.getCurrentDate());
        numberLessonsOfSubjectForPass = new ArrayList<>();

        if (subjectsForPassToday.size() == 0) {
            System.out.println("No subject for pass today!");
            return;
        }

        for (int i = 0; i < subjectsForPassToday.size(); i++) {
             ArrayList<Integer> numbersOfLessonsForOneSubject = dataBase.getNumberOfLessonsForPassedSubjects(subjectsForPassToday.get(i), dateUtil.getCurrentDate());
             for (int j = 0; i < numbersOfLessonsForOneSubject.size(); j++){
                 numberLessonsOfSubjectForPass.add(numbersOfLessonsForOneSubject.get(j));
             }
        }
    }

    public void checkLabsStatys(){
        ArrayList<Date> endOfLessons = dataBase.getEndOfLessons();
        for (int i = 0; i < numberLessonsOfSubjectForPass.size(); i++){
            int subject = dataBase.getSubjectByLessonNuberAtDay(numberLessonsOfSubjectForPass.get(i),dateUtil.getCurrentDate());
            if (dateUtil.getCurrentTime().getTime() >= endOfLessons.get(numberLessonsOfSubjectForPass.get(i)-1).getTime()){
                if(dataBase.getlabStatus(subject,dateUtil.getCurrentDate()) == inProcess) dataBase.setLabStatus(subject,dateUtil.getCurrentDate(),debt);
            }
        }

        if(dateUtil.isMoreLessonsToday(dataBase)) startBotTimer();
    }

    public void startBotTimer(){
        Timer timer = new Timer();
        BotCronTask botCronTask = new BotCronTask(timer);
        timer.schedule(botCronTask,dateUtil.getTimeToNextLesson(dataBase));
    }
}
