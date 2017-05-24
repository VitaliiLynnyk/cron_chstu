package chstu.timetable;

import chstu.db.DBAdapter;
import chstu.db.Labs;

import java.util.ArrayList;

/**
 * Created by alex3 on 5/20/2017.
 */
public class Tasks {
    final int startDate = 15;
    final int finishDate = 26;
    DBAdapter dbAdapter = new DBAdapter();
    ArrayList<String> arrayDates = new ArrayList<>();

    private int getNumberLessons(int subject){
        int count = 0;
        for (int i = startDate; i <= finishDate; i++){
            count+=dbAdapter.getCountLessonsOfSubject(subject,convertIntToDate(i));
            if(dbAdapter.getCountLessonsOfSubject(subject,convertIntToDate(i)) > 0){
                arrayDates.add(convertIntToDate(i));
            }
        }
        return count;
    }


    public void setLabs(int subject, int numberLabs){
        int interval = getNumberLessons(subject)/numberLabs;
        int id = maxIdLabs();
        int count = numberLabs;
        for (int i = 0; i < arrayDates.size(); i+=interval){
            if (count > 0){
                dbAdapter.setNewLab(id,subject,id+1,"",arrayDates.get(i),0);
                id++;
                count--;
            }
        }
        arrayDates.clear();
    }

    private String convertIntToDate(int day){
        return "2017-05-"+day;
    }

    private int maxIdLabs(){
        int max;
        if (dbAdapter.getAllLabs().size()>0) {
            max = dbAdapter.getAllLabs().get(0).getId();
        }
        else {
            max = -1;
        }
        for (int i = 0; i < dbAdapter.getAllLabs().size(); i++) {
            if (dbAdapter.getAllLabs().get(i).getId() >= max){
                max = dbAdapter.getAllLabs().get(i).getId();
            }
        }
        return (max+1);
    }
}
