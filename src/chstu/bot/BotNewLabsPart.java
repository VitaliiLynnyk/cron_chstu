package chstu.bot;

import chstu.db.DBAdapter;
import chstu.db.entity.Laboratory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex3 on 5/20/2017.
 */
public class BotNewLabsPart {
    final int startDate = 15;
    final int finishDate = 26;
    DBAdapter dbAdapter = DBAdapter.getInstance();
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
        int id = getMaxIdLabs();
        int labNumber = getMaxLabNumber(subject);
        int count = numberLabs;
        for (int i = 0; i < arrayDates.size(); i+=interval, labNumber++){
            if (count > 0){
                dbAdapter.setNewLab(id,subject,labNumber,"",arrayDates.get(i),0);
                id++;
                count--;
            }
        }
        arrayDates.clear();
    }

    private String convertIntToDate(int day){
        return "2017-05-"+day;
    }

    private int getMaxIdLabs(){
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

    private int getMaxLabNumber(int subject){
        int maxLabNumber = 0;
        List<Laboratory> subjectLabs = dbAdapter.getLabsBySubject(subject);
        for (Laboratory lab : subjectLabs){
            if (lab.getLabNumber() >= maxLabNumber) maxLabNumber = lab.getLabNumber();
        }

        return maxLabNumber + 1;
    }
}