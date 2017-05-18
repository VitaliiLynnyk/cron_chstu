package chstu.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class DBAdapter {
    public  DBAdapter() {
        try{
            Class.forName("org.sqlite.JDBC");
            conector = DriverManager.getConnection("jdbc:sqlite:chdtu.db");
            conector.setAutoCommit(false);
            statement = conector.createStatement();
            System.out.println("Opened database successfully");
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Connection to DB faild");
        }
    }

    private static DBAdapter instance;
    public static DBAdapter getInstance(){
        if (instance == null) {
            instance = new DBAdapter();
        }

        return  instance;
    }

    Connection conector = null;
    Statement statement = null;

    public List<Labs> getListOfLabs(){
        List<Labs> labsList = new ArrayList<>();
        String sqlTask = "SELECT * FROM labs;";

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int id = result.getInt("id");
                int numberLesson = result.getInt("number_lesson");
                int idSubject = result.getInt("id_subject");
                String lessonDate = result.getString("lesson_date");
                int typeLesson = result.getInt("type_lesson");
                labsList.add(new Labs(id,numberLesson,idSubject,lessonDate,typeLesson));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Labs can`t be get!");
        }

        return labsList;
    }

    public List<LessonTimetable> getLessonTimetable(){
        List<LessonTimetable> timetableList = new ArrayList<>();
        String sqlTask = "SELECT * FROM lesson_timetable;";

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int id = result.getInt("id");
                String endLesson = result.getString("end_lesson");
                timetableList.add(new LessonTimetable(id,endLesson));
            }

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Timetable is secret.");
        }

        return timetableList;
    }

    public List<Subjects> getAllSubjects(){
        List<Subjects> subjectsList = new ArrayList<>();
        String sqlTask = "SELECT * FROM subjects;";

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                subjectsList.add(new Subjects(id,name));
            }

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Timetable is secret.");
        }

        return subjectsList;
    }

}
