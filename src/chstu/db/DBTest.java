package chstu.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ar-Krav on 13.05.2017.
 */
public class DBTest {
    public static void main(String[] args) {
        /*DBAdapter dbAdapter = new DBAdapter();

        ArrayList<String> ar = dbAdapter.getNamesOfSubjects();
        for (int i = 0; i < ar.size(); i++) System.out.println(ar.get(i));*/

        String s1 = "18:17:00";
        String s2 = "17:20:21";

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date d1 = null;
        Date d2 = null;

        try{
            d1 = sdf.parse(s1);
            d2 = sdf.parse(s2);
        }
        catch (Exception e){

        }

        if (d1.getTime() > d2.getTime())System.out.println("all work");
    }
}
