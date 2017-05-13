package chstu.db;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Ar-Krav on 13.05.2017.
 */
public class DBAdapter {
    public DBAdapter() {
        try{
            Class.forName("org.sqlite.JDBC");
            conector = DriverManager.getConnection("jdbc:sqlite:test.db");
            conector.setAutoCommit(false);
            System.out.println("Opened database successfully");
        }
        catch (Exception e){
            System.out.println("Connection to DB faild");
        }
    }

    Connection conector = null;
    Statement statement = null;

    public ArrayList<String> getNamesOfSubjects(){
        ArrayList <String> namesOfSubjects = new ArrayList<String>();

        String sqlTask = "SELECT * FROM subjects;";
        try{
            ResultSet resultSet = statement.executeQuery(sqlTask);
            while (resultSet.next()){
                namesOfSubjects.add(resultSet.getString("name"));
            }
        }
        catch (Exception e){
            System.out.println("Cannot get name of subjects");
        }

        return  namesOfSubjects;
    }

    public

}
