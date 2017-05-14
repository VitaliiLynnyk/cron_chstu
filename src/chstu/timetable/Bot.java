package chstu.timetable;

import chstu.db.DBAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Bot {
    public Bot() {
        currentDate = new Date();
        dataBase = new DBAdapter();
    }

    Date currentDate;
    DBAdapter dataBase;
    ArrayList<Integer> subjectsForPassToday;
    ArrayList<Integer> numberLessonsOfSubjectForPass;
    int inProcess = 0, passedLab = 1, debt = 2;

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }

    private Date getCurrentTime(){
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

    public void checkUserDutyForToday() {
        subjectsForPassToday = dataBase.getSubjectsForPass(getCurrentDate());
        numberLessonsOfSubjectForPass = new ArrayList<>();

        if (subjectsForPassToday.size() == 0) {
            System.out.println("No subject for pass today!");
            return;
        }

        for (int i = 0; i < subjectsForPassToday.size(); i++) {
             ArrayList<Integer> numbersOfLessonsForOneSubject = dataBase.getNumberOfLessonsForPassedSubjects(subjectsForPassToday.get(i), getCurrentDate());
             for (int j = 0; i < numbersOfLessonsForOneSubject.size(); j++){
                 numberLessonsOfSubjectForPass.add(numbersOfLessonsForOneSubject.get(j));
             }
        }
    }

    private void checkLabsStatys(){
        ArrayList<Date> endOfLessons = dataBase.getEndOfLessons();
        for (int i = 0; i < numberLessonsOfSubjectForPass.size(); i++){
            if (getCurrentTime().getTime() >= endOfLessons.get(numberLessonsOfSubjectForPass.get(i)-1).getTime()){
                //dataBase.setLabStatus(dataBase.getSubjectBylessonnuberAtDay(numberLessonsOfSubjectForPass));
            }
        }
    }
}
