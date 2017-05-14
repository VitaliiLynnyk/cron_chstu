package chstu.db;

import chstu.timetable.Bot;
import chstu.timetable.DateUtil;

import java.text.SimpleDateFormat;

public class DBTest {
    public static void main(String[] args) {
        /*DBAdapter dataBase = new DBAdapter();

        for (int i = 0; i < dataBase.getEndOfLessons().size(); i++){
            System.out.println(dataBase.getEndOfLessons().get(i));
        }*/

        String str = "09:20:00";
        /*SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
        try{
            System.out.println(s.parse(str));
        }
        catch (Exception e){}*/

        DateUtil du = new DateUtil();
        System.out.println(du.getCurrentTime().getTime());
    }
}
