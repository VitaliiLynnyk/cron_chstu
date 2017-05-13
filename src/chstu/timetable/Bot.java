package chstu.timetable;

import chstu.db.DBAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Bot {
    public Bot() {
        currentDate = new Date();
        dbAdapter = new DBAdapter();
    }

    Date currentDate;
    DBAdapter dbAdapter;

    private String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate).toString();
    }

    private int getTypeOfWeek(){
        int pairWeek = 1, nonPairWeek = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("w");

        if(Integer.parseInt(dateFormat.toString())%2 == 0) return pairWeek;
        else return nonPairWeek;
    }

    private int getNumbefDayOfWeek(){
        String dayOfWeek = currentDate.toString().substring(0,3);

        switch (dayOfWeek){
            case "Mon": return 1;
            case "Tue": return 2;
            case "Wed": return 3;
            //case ""
            default: return 0;
        }
    }

    public void checkUserActivity(){
        ArrayList<Integer> subjectForPass = dbAdapter.getSubjectsForPass(getDate());

        if (subjectForPass.size() == 0) {
            System.out.println("No subject for pass today!");
            return;
        }

        for (int i = 0; i < subjectForPass.size(); i++){
            //ArrayList<Integer> numberLessonsOfSubjectForPass = dbAdapter.getNumberOfLessonsForPassedSubjects(subjectForPass.get(i),,getTypeOfWeek());
        }
    }
}
