package chstu.timetable;

import chstu.db.DBAdapter;

import java.util.Date;

public class Bot {
    public Bot() {
        currentDate = new Date();
        dbAdapter = new DBAdapter();
    }

    Date currentDate;
    DBAdapter dbAdapter;
}
