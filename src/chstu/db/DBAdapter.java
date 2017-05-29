package chstu.db;

import chstu.db.entity.*;

import java.sql.*;
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
            System.out.println("Connection to DB failed");
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

    /*
        Methods for get ALL fields from tables of DB
    */

    public List<BellTimetable> getLessonTimetable(){
        List<BellTimetable> timetableList = new ArrayList<>();
        String sqlTask = "SELECT * FROM lesson_timetable;";

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int id = result.getInt("id");
                String endLesson = result.getString("end_lesson");

                timetableList.add(new BellTimetable(id,endLesson));
            }

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Lesson timetable is secret.");
        }

        return timetableList;
    }

    public List<Subject> getAllSubjects(){
        List<Subject> subjectList = new ArrayList<>();
        String sqlTask = "SELECT * FROM subjects ORDER BY id;";

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");

                subjectList.add(new Subject(id,name));
            }

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Can`t get subjects.");
        }

        return subjectList;
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

    /*
        Methods for working with "labs" table
    */

    public List<Laboratory> getAllLabs(){
        String sqlTask = "SELECT * FROM labs;";
        return getListOfLabs(sqlTask);
    }

    public List<Laboratory> getDebtLabs(){
        String sqlTask = "SELECT * FROM labs" +
                " WHERE status = 2;";
        return getListOfLabs(sqlTask);
    }

    public List<Laboratory> getLabsByDaySubject(String deadline, int subject){
        String sqlTask = "SELECT * FROM labs" +
                " WHERE deadline = '" + deadline + "' AND id_subject = " + subject +";";
        return getListOfLabs(sqlTask);
    }

    public List<Laboratory> getLabsByDay(String deadline){
        String sqlTask = "SELECT * FROM labs" +
                " WHERE deadline = '" + deadline + "';";
        return getListOfLabs(sqlTask);
    }

    public List<Laboratory> getLabsBySubject(int subject){
        String sqlTask = "SELECT * FROM labs" +
                " WHERE id_subject = '" + subject + "'" +
                " ORDER BY lab_number;";
        return getListOfLabs(sqlTask);
    }

    private List<Laboratory> getListOfLabs(String sqlTask){
        List<Laboratory> laboratoryList = new ArrayList<>();

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int id = result.getInt("id");
                int idSubject = result.getInt("id_subject");
                int labNumber = result.getInt("lab_number");
                String comment = result.getString("comment");
                String deadline = result.getString("deadline");
                int status = result.getInt("status");

                laboratoryList.add(new Laboratory(id,idSubject,labNumber,comment,deadline,status));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Laboratory can`t be get!");
        }

        return laboratoryList;
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

    public void updateLabComment(String newComment, int idSubject, int labNumber){
        String sqlTask = "UPDATE labs" +
                " SET comment = '" + newComment + "'" +
                " WHERE id_subject = " + idSubject + " AND lab_number = " + labNumber + ";";
        updateLabInfo(sqlTask);
    }

    public void updateLabDeadline(String newDeadline, int idSubject, int labNumber){
        String sqlTask = "UPDATE labs" +
                " SET deadline = '" + newDeadline + "'" +
                " WHERE id_subject = " + idSubject + " AND lab_number = " + labNumber + ";";
        updateLabInfo(sqlTask);
    }

    public void updateLabStatus(int newStatus, int idSubject, int labNumber){
        String sqlTask = "UPDATE labs" +
                " SET status = " + newStatus +
                " WHERE id_subject = " + idSubject + " AND lab_number = " + labNumber + ";";
        updateLabInfo(sqlTask);
    }

    private void updateLabInfo(String sqlTask){
        try{
            statement.executeUpdate(sqlTask);
            conector.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("can`t update information about lab.");
        }
    }

    /*
        Methods for working with timetable.
    */

    public List<LessonTimetable> getLessonsForSubjectInDay(int subject, String dayDate){
        String sqlTask = "SELECT * FROM timetable" +
                " WHERE id_subject = " + subject + " AND lesson_date = '" + dayDate + "';";
        return getTimetable(sqlTask);
    }

    public List<LessonTimetable> getLessonsInDay(String dayDate){
        String sqlTask = "SELECT * FROM timetable" +
                " WHERE lesson_date = '" + dayDate + "';";
        return getTimetable(sqlTask);
    }

    public List<Lesson> getLessonsToShow(String dayDate){
        List<Lesson> lessonsForShow = new ArrayList<>();
        String sqlTask = "SELECT number_lesson, subjects.name AS sName, type_lesson.name AS tlName FROM timetable" +
                " INNER JOIN subjects ON timetable.id_subject = subjects.ID" +
                " INNER JOIN type_lesson ON timetable.type_lesson = type_lesson.id" +
                " WHERE lesson_date = '" + dayDate + "';";

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int numberLesson = result.getInt("number_lesson");
                String lessoonSubject = result.getString("sName");
                String typeLesson = result.getString("tlName");

                lessonsForShow.add(new Lesson(numberLesson,lessoonSubject,typeLesson));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Impossible get lessons to show for input date.");
        }
        return lessonsForShow;
    }
    private List<LessonTimetable> getTimetable(String sqlTask){
        List<LessonTimetable> lessonTimetableList = new ArrayList<>();

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            while (result.next()){
                int id = result.getInt("id");
                int numberLesson = result.getInt("number_lesson");
                int idSubject = result.getInt("id_subject");
                String lessonDate = result.getString("lesson_date");
                int typeLesson = result.getInt("type_lesson");

                lessonTimetableList.add(new LessonTimetable(id,numberLesson,idSubject,lessonDate,typeLesson));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("LessonTimetable for group can`t get.");
        }

        return lessonTimetableList;
    }




    public int getCountLessonsOfSubject(int subject, String dayDate){
        int countOfLessons = 0;

        String sqlTask = "SELECT COUNT(id) AS num FROM timetable" +
                " WHERE lesson_date = '" + dayDate + "' AND id_subject = " + subject +
                " AND type_lesson = (SELECT id FROM type_lesson WHERE name = \"Лабораторні\");";

        try{
            ResultSet result = statement.executeQuery(sqlTask);
            countOfLessons = result.getInt("num");
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Can`t read date of subject in a day.");
        }

        return countOfLessons;
    }}

