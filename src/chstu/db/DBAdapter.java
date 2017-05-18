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

    private Connection conector = null;
    private Statement statement = null;

    public List<Labs> getListOfLabs(){
        List<Labs> labsList = new ArrayList<>();
        String sqlTask = "SELECT * FROM labs;";

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int id = result.getInt("id");
                int idSubject = result.getInt("id_subject");
                int labNumber = result.getInt("lab_number");
                String comment = result.getString("comment");
                String deadline = result.getString("deadline");
                int status = result.getInt("status");

                labsList.add(new Labs(id,idSubject,labNumber,comment,deadline,status));
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
            System.out.println("Lesson timetable is secret.");
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
            System.out.println("Can`t get subjects.");
        }

        return subjectsList;
    }

    public List<Timetable> getTimetable(){
        List<Timetable> timetableList = new ArrayList<>();
        String sqlTask = "SELECT * FROM timetable;";

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int id = result.getInt("id");
                int numberLesson = result.getInt("number_lesson");
                int idSubject = result.getInt("id_subject");
                String lessonDate = result.getString("lesson_date");
                int typeLesson = result.getInt("type_lesson");

                timetableList.add(new Timetable(id,numberLesson,idSubject,lessonDate,typeLesson));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Timetable for group can`t get.");
        }

        return timetableList;
    }

    public List<TypeLesson> getAllLessonsType(){
        List<TypeLesson> lessonsTypeList = new ArrayList<>();
        String sqlTask = "SELECT * FROM type_lesson;";

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");

                lessonsTypeList.add(new TypeLesson(id,name));
            }

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Lessons type are not avaliable.");
        }

        return lessonsTypeList;
    }

    public void setNewLab(int id, int idSubject, int labNumber, String comment, String deadline, int status){
        String sqlTask = "INSERT INTO labs" +
                         " VALUES (" + id + ", " + idSubject + ", " + labNumber + ", '" + comment + "', '" + deadline + "', " + status + ");";

        try{
            statement.executeUpdate(sqlTask);
            conector.commit();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Can`t add new lab.");
        }
    }

}
