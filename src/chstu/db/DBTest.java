package chstu.db;

import java.util.ArrayList;

/**
 * Created by Ar-Krav on 13.05.2017.
 */
public class DBTest {
    public static void main(String[] args) {
        DBAdapter dbAdapter = new DBAdapter();

        ArrayList<String> ar = dbAdapter.getNamesOfSubjects();
        for (int i = 0; i < ar.size(); i++) System.out.println(ar.get(i));
    }
}
