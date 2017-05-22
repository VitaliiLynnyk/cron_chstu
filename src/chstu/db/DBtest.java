package chstu.db;


import chstu.timetable.Bot;
import chstu.timetable.DateUtil;

import java.util.*;

public class DBtest {
    public static void main(String[] args) {
        DBAdapter dataBase = DBAdapter.getInstance();
        Bot bot = new Bot();
        DateUtil dateUtil = new DateUtil();

        /*System.out.println(dataBase.getLabsByDay("2017-05-22").get(0).getIdSubject());
        System.out.println(dateUtil.getCurrentDate());
        bot.startBot();
        System.out.println("Bot should be active!");*/

        java.util.List<Lesson> ls = dataBase.getLessonsToShow("2017-05-22");

        System.out.println(ls.get(0).getName()+ls.get(0).getNumber()+ls.get(0).getType());
    }
}
