package chstu.db;


import javax.swing.*;
import java.awt.event.ActionListener;

public class DBtest {
    public static void main(String[] args) {
        DBAdapter db = DBAdapter.getInstance();

        System.out.println(db.getCountLessonsOfSubject(1,"2017-05-15"));
    }
}
