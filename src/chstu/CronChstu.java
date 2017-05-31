package chstu;

import chstu.gui.Viewport;
import chstu.bot.BotProgressCheckedPart;


// todo it;s main file, but name is strange, could it be Main ?
public class CronChstu {

    public static void main(String[] args) {
        Viewport form = new Viewport();
            form.makeForm();

        BotProgressCheckedPart botMainSystem = new BotProgressCheckedPart();
            botMainSystem.checkPreviousDates();
            botMainSystem.remindDebts();
            botMainSystem.startCheckProgress();
    }
}
