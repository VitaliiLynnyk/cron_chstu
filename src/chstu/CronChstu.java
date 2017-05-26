package chstu;

import chstu.gui.Viewport;
import chstu.timetable.Bot;


public class CronChstu {

    public static void main(String[] args) {
        Viewport form = new Viewport();
            form.makeForm();

        Bot bot = new Bot();
            bot.checkPreviousDates();
            bot.remindDebts();
            bot.startBot();
    }
}
