package chstu;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by linni on 5/13/2017.
 */
public class Calendars {
    int Date;
    int Month;
    int Year;

    public int getDaysInMonth() {
        return daysInMonth;
    }

    public void setDaysInMonth(int daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    int daysInMonth;
    public int getDate() {
        return Date;
    }

    public void setDate(int date) {
        Date = date;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public void date(){
        Calendar calendar = Calendar.getInstance();

        Date = calendar.get(Calendar.DAY_OF_MONTH);
        Month = calendar.get(Calendar.MONTH)+1;
        Year = calendar.get(Calendar.YEAR);
        daysInMonth = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
    }

    public static void printCalendarMonthYear(int month, int year) {
        // create a new GregorianCalendar object
        Calendar cal = new GregorianCalendar();

        // set its date to the first day of the month/year given by user
        cal.clear();
        cal.set(year, month - 1, 1);

        // print calendar header
        System.out.println("\n"
                + cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                Locale.US) + " " + cal.get(Calendar.YEAR));

        // obtain the weekday of the first day of month.
        int firstWeekdayOfMonth = cal.get(Calendar.DAY_OF_WEEK);

        // obtain the number of days in month.
        int numberOfMonthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // print anonymous calendar month based on the weekday of the first
        // day of the month and the number of days in month:
        printCalendar(numberOfMonthDays, firstWeekdayOfMonth);
    }
    private static void printCalendar(int numberOfMonthDays, int firstWeekdayOfMonth) {

        // reset index of weekday
        int weekdayIndex = 0;

        // print calendar weekday header
        System.out.println("Su  Mo  Tu  We  Th  Fr  Sa");

        // leave/skip weekdays before the first day of month
        for (int day = 1; day < firstWeekdayOfMonth; day++) {
            System.out.print("    ");
            weekdayIndex++;
        }

        // print the days of month in tabular format.
        for (int day = 1; day <= numberOfMonthDays; day++) {
            // print day
            System.out.printf("%1$2d", day);

            // next weekday
            weekdayIndex++;
            // if it is the last weekday
            if (weekdayIndex == 7) {
                // reset it
                weekdayIndex = 0;
                // and go to next line
                System.out.println();
            } else { // otherwise
                // print space
                System.out.print("  ");
            }
        }

        // print a final new-line.
        System.out.println();
    }
}
