package chstu.timetable;

import chstu.db.DBAdapter;

import javax.security.auth.Subject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by alex3 on 5/13/2017.
 */
public class Tasks {
    final public String startSemester = "2017-05-15";
    final public String endSemester = "2017-05-29";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    DBAdapter dbAdapter = new DBAdapter();
    public String addDate(String buf,int number){

        Calendar cl = Calendar.getInstance();
        try {
            cl.setTimeInMillis(dateFormat.parse(buf).getTime()+ (1000 * 60 * 60 * 24 * number));
            String newDate = dateFormat.format(cl.getTime());
            return newDate;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    int  getDay(String subject){
        Calendar cl = Calendar.getInstance();
        try {
            cl.setTime(dateFormat.parse(subject));
            int day = cl.get(Calendar.DAY_OF_MONTH);
            return day;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int numberLesson(int subject){
        String copy = startSemester;
        int result = 0;
        do {
            copy = addDate(copy,1);
            for(int i=0;i<dbAdapter.getSubjectsByDayDate(copy).size();i++){System.out.print("!!!!!!!!!!");
                String s = (dbAdapter.getSubjectsByDayDate(copy).get(i)).toString();
                if(subject == Integer.parseInt(s)){
                    result++;
                }
            }
        } while (getDay(copy) <= getDay(endSemester));
        return result;
    }

    /*int numberLessons(int day){
        int result = 0;
        for(int j=1; j<3;j++) {
            for (int i = 1; i < 6; i++)
                for (int k=0; k<(dbAdapter.(i,j)).size();k++){
                if(day+1 == Integer.parseInt((dbAdapter.getSubjectsByDayNumber(i,j).get(k)).toString())){
                    result++;
                }
            }
        }
        System.out.print(result);
        return result;
    }

    public void createNewLabs(int subject,int number){
        int interval = Math.round(number/numberLessons(subject));
        //int interval = 3;

        System.out.print(interval);
        String buf = startSemester;
        while (true){
            buf = setDay(buf,getDay(interval));
            if(getDay(interval) <= Integer.parseInt(endSemester.charAt(9)+""+endSemester.charAt(10)) )
                dbAdapter.setNewLab(subject,number,"",buf,0);
            else
                return;
        }

    }

    int  getDay(int interval){
        return Integer.parseInt(startSemester.charAt(9)+""+startSemester.charAt(10))+interval;
    }

    String setDay(String date,int day){
        String result = "";
        for(int i=0; i<9;i++) {
            result += startSemester.charAt(i);
        }
        return result+day;

    }*/


}
