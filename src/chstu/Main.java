package chstu;

import chstu.Interface.Calendars;
import chstu.Interface.GUI;

public class Main {

    public static void main(String[] args) {
        Calendars calendars = new Calendars();
        GUI form = new GUI();
        form.makeForm();
    }
}
