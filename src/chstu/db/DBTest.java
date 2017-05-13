package chstu.db;

/**
 * Created by Ar-Krav on 13.05.2017.
 */
public class DBTest {
    public static void main(String[] args) {
        DBAdapter dbAdapter = new DBAdapter();

        dbAdapter.getAllLessonsOfDay(1,0);
    }
}
