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

    public void checkUserActivity(){
        ArrayList<Integer> subjectForPass = dbAdapter.getSubjectsForPass(getDate());

        if (subjectForPass.size() == 0) {
            System.out.println("No subject for pass today!");
        }
    }
}
