package chstu.db;

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

    public List<Labs> getAllLabs(){
        String sqlTask = "SELECT * FROM labs;";
        return getListOfLabs(sqlTask);
    }

    public List<Labs> getLabsByDaySubject(String deadline, int subject){
        String sqlTask = "SELECT * FROM labs" +
                " WHERE deadline = " + deadline + " AND id_subject = " + subject +";";
        return getListOfLabs(sqlTask);
    }

    public List<Labs> getLabsByDay(String deadline){
        String sqlTask = "SELECT * FROM labs" +
                         " WHERE deadline = " + deadline + ";";
        return getListOfLabs(sqlTask);
    }

    public List<Labs> getLabsBySubject(int subject){
        String sqlTask = "SELECT * FROM labs" +
                " WHERE id_subject = " + subject + ";";
        return getListOfLabs(sqlTask);
    }

    private List<Labs> getListOfLabs(String sqlTask){
        List<Labs> labsList = new ArrayList<>();

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
                         " SET comment = " + newComment +
                         " WHERE id_subject = " + idSubject + " AND lab_number = " + labNumber + ";";
        updateLabInfo(sqlTask);
    }

    public void updateLabDeadline(String newDeadline, int idSubject, int labNumber){
        String sqlTask = "UPDATE labs" +
                " SET deadline = " + newDeadline +
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

    public List<Timetable> getLessonsForSubjectInDay(int subject, String dayDate){
        String sqlTask = "SELECT * FROM timetable" +
                         " WHERE id_subject = " + subject + " AND lesson_date = " + dayDate + ";";
        return getTimetable(sqlTask);
    }

    public List<Timetable> getLessonsInDay(String dayDate){
        String sqlTask = "SELECT * FROM timetable" +
                " WHERE lesson_date = " + dayDate + ";";
        return getTimetable(sqlTask);
    }

    private List<Timetable> getTimetable(String sqlTask){
        List<Timetable> timetableList = new ArrayList<>();

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
    }
}
