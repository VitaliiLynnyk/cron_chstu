package chstu.timetable;

import chstu.db.DBAdapter;
import chstu.db.Labs;
import chstu.db.LessonTimetable;
import chstu.db.Timetable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class Bot {
    public Bot() {
        currentDate = new Date();
        dataBase = DBAdapter.getInstance();
    }

    private Date currentDate;
    private DBAdapter dataBase;
    private DateUtil dateUtil = new DateUtil();

    private List<Labs> labsForPassToday;
    private List<Timetable> lessonForPass;
    private int inProcess = 0, debt = 2;


    public void startBot(){
        if (!haveUserDutyForToday()){
            startTimer(dateUtil.getTimeToNextDayLesson());
            return;
        }

        remindDebts();

        for(Timetable lesson : lessonForPass){
            if(dateUtil.getCurrentTimeMS() >= getLessonTimeInMS(lesson.getNumberLesson())){
                System.out.println(getLessonTimeInMS(lesson.getNumberLesson()));
                for(Labs lab : labsForPassToday){
                    if (lab.getIdSubject() == lesson.getIdSubject() && lab.getStatus() == inProcess) {
                        dataBase.updateLabStatus(debt,lab.getIdSubject(),lab.getLabNumber());
                        showNotification("Сьогодні мала бути здана лабораторна робота №" + lab.getLabNumber(), TrayIcon.MessageType.INFO);
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

    private void remindDebts(){
        if (dataBase.getDebtLabs().size() > 0){
            showNotification("Ви маєте заборговані предмети!\nПеревірте які саме та починайте здавати борги!", TrayIcon.MessageType.WARNING);
        }
    }

    private void startTimer(long delay){
        Timer timer = new Timer();
        BotCronTask botCronTask = new BotCronTask(timer);
        timer.schedule(botCronTask,delay);
    }
}
