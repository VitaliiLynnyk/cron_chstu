package chstu;

public class Main {

    public static void main(String[] args) {
        GUI form = new GUI();
        form.makeForm();

        Calendars calendars = new Calendars();
        calendars.date();
        calendars.printCalendarMonthYear(calendars.getMonth(),calendars.getYear());
    }
}
