package chstu;

import chstu.gui.Viewport;
import chstu.bot.BotProgressCheckedPart;


public class Main {

    public static void main(String[] args) {
        Viewport form = new Viewport();
            form.makeForm();

        BotProgressCheckedPart botMainSystem = new BotProgressCheckedPart();
            botMainSystem.checkPreviousDates();
            botMainSystem.remindDebts();
            botMainSystem.startCheckProgress();
    }
}
