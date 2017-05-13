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
            conector = DriverManager.getConnection("jdbc:sqlite:chdtu.db");
            conector.setAutoCommit(false);
            System.out.println("Opened database successfully");
        }
        catch (Exception e){
            e.printStackTrace();
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

    public ArrayList<ArrayList<String>> getAllLessonsOfDay(int dayNumber, int weekType){
        ArrayList <String> lessonInfo = new ArrayList<String>();
        ArrayList<ArrayList<String>> listOfLessons = new ArrayList<>();

        String sqlTask = " SELECT number_lesson, subjects.name, type_lesson.name FROM timetable" +
                         " INNER JOIN subjects ON timetable.id_subject = subjects.id" +
                         " INNER JOIN type_lesson ON timetable.type_lesson = type_lessons.id" +
                         " WHERE day_number = " + dayNumber +" AND week_type =" + weekType;
        try{
            ResultSet resultSet = statement.executeQuery(sqlTask);
            while (resultSet.next()){
                lessonInfo.add("" + resultSet.getInt("number_lesson"));
                lessonInfo.add(resultSet.getString("subjects.name"));
                lessonInfo.add(resultSet.getString("type_lesson.name"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Cannot get name of subjects");
        }

        return  listOfLessons;
    }

}
