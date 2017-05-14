package chstu;

import chstu.Interface.Calendars;
import chstu.Interface.GUI;
import chstu.db.DBAdapter;
import chstu.timetable.DateUtil;

import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        GUI form = new GUI();
        form.makeForm();

        DBAdapter db = DBAdapter.getInstance();

        ArrayList<ArrayList<String>> ar = db.getAllLessonsOfDay("2017-05-17");

        for (int i = 0; i < ar.size(); i++)
            System.out.println(ar.get(0).get(1));
    }


}
