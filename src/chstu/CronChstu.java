package chstu;

import chstu.Interface.GUI;
import chstu.timetable.Bot;
import chstu.timetable.Tasks;


public class CronChstu {

    public static void main(String[] args) {
        GUI form = new GUI();
            form.makeForm();

        Bot bot = new Bot();
            bot.checkPreviousDates();
            bot.remindDebts();
            bot.startBot();
    }
}
