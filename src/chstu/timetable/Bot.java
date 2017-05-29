package chstu.timetable;

import chstu.db.*;
import chstu.db.entity.Laboratory;
import chstu.db.entity.BellTimetable;
import chstu.db.entity.Subject;
import chstu.db.entity.LessonTimetable;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Bot {
    public Bot() {
        currentDate = new Date();
        dataBase = DBAdapter.getInstance();
    }

    private Date currentDate;
    private DBAdapter dataBase;
    private DateUtil dateUtil = new DateUtil();

    private List<Laboratory> laboratoryForPassToday;
    private List<LessonTimetable> lessonForPass;
    private int inProcess = 0, debt = 2;


    public void startBot(){
        if (!haveUserDutyForToday()){
            startTimer(dateUtil.getTimeToNextDayLesson());
            return;
        }

        for(LessonTimetable lesson : lessonForPass){
            if(dateUtil.getCurrentTimeMS() >= getLessonTimeInMS(lesson.getNumberLesson())){
                System.out.println(getLessonTimeInMS(lesson.getNumberLesson()));
                for(Laboratory lab : laboratoryForPassToday){
                    if (lab.getIdSubject() == lesson.getIdSubject() && lab.getStatus() == inProcess) {
                        dataBase.updateLabStatus(debt,lab.getIdSubject(),lab.getLabNumber());
                        showNotification("Сьогодні мала бути здана лабораторна робота!" +
                                                  "\n" + getLabSubjectName(lab) + " | №" + lab.getLabNumber(), TrayIcon.MessageType.INFO);
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

    private boolean haveUserDutyForToday() {
        laboratoryForPassToday = dataBase.getLabsByDay(dateUtil.getCurrentDate());
        lessonForPass = new ArrayList<>();

        if (laboratoryForPassToday.size() == 0) {
            System.out.println("No subject for pass today!");
            return false;
        }

        for(Laboratory lab : laboratoryForPassToday){
            lessonForPass.addAll(dataBase.getLessonsForSubjectInDay(lab.getIdSubject(),lab.getDeadline()));
        }

        return true;
    }

    private long getLessonTimeInMS(int numberLesson){
        long lessonTime = -1;
        List<BellTimetable> endOfLessons = dataBase.getLessonTimetable();

        for (BellTimetable timetable : endOfLessons){
            if (timetable.getId() == numberLesson){
                lessonTime = dateUtil.convertTimeInMS(timetable.getEndLesson());
            }
        }

        return lessonTime;
    }

    private String getLabSubjectName(Laboratory lab){
        List<Subject> subjectList = dataBase.getAllSubjects();

        for (Subject subject : subjectList){
            if (lab.getIdSubject() == subject.getId()){
                    return subject.getName();
            }
        }

        return "";
    }

    private void showNotification(String message, TrayIcon.MessageType messageType){
        if (!SystemTray.isSupported()){
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();

        java.awt.Image image = Toolkit.getDefaultToolkit().getImage("images/tray.gif");
        TrayIcon trayIcon = new TrayIcon(image);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        trayIcon.displayMessage("CRON_CHSTU", message, messageType);
    }

    private void startTimer(long delay){
        Timer timer = new Timer();
        BotCronTask botCronTask = new BotCronTask(timer);
        timer.schedule(botCronTask,delay);
    }

    public void remindDebts(){
        if (dataBase.getDebtLabs().size() > 0){
            showNotification("Ви маєте заборговані предмети!\nПеревірте які саме та починайте здавати борги!", TrayIcon.MessageType.WARNING);
        }
    }

    public void checkPreviousDates(){
        List<Laboratory> allLAbs = dataBase.getAllLabs();
        Calendar currentDate = dateUtil.convertStringInDate(dateUtil.getCurrentDate());
        Calendar labDate;

        for(Laboratory lab : allLAbs){
            labDate = dateUtil.convertStringInDate(lab.getDeadline());
            if (currentDate.after(labDate) && lab.getStatus() != 1){
                dataBase.updateLabStatus(2,lab.getIdSubject(),lab.getLabNumber());
            }
        }
    }
}
