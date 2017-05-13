package chstu.db;

import java.sql.*;
import java.util.ArrayList;


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

    Connection conector = null;
    Statement statement = null;

    public ArrayList<String> getEndOfLessons(){
        ArrayList <String> endOflessonsList = new ArrayList<String>();

        String sqlTask = "SELECT * FROM lessons_timetable;";
        try{
            ResultSet resultSet = statement.executeQuery(sqlTask);
            while (resultSet.next()) endOflessonsList.add(resultSet.getString("lessons_end"));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Cannot get lessons timetable");
        }

        return  endOflessonsList;
    }

    public ArrayList<String> getNamesOfSubjects(){
        ArrayList <String> namesOfSubjects = new ArrayList<String>();

        String sqlTask = "SELECT * FROM subjects;";
        try{
            ResultSet resultSet = statement.executeQuery(sqlTask);
            while (resultSet.next()) namesOfSubjects.add(resultSet.getString("name"));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Cannot get name of subjects");
        }

        return  namesOfSubjects;
    }

    public ArrayList<ArrayList<String>> getAllLessonsOfDay(int dayNumber, int weekType){
        ArrayList <String> lessonInfo;
        ArrayList<ArrayList<String>> listOfLessons = new ArrayList<>();

        String sqlTask = " SELECT number_lesson, subjects.name, type_lesson.name FROM timetable" +
                " INNER JOIN subjects ON timetable.id_subject = subjects.id" +
                " INNER JOIN type_lesson ON timetable.type_lesson = type_lessons.id" +
                " WHERE day_number = " + dayNumber +" AND week_type =" + weekType;

        try{
            ResultSet resultSet = statement.executeQuery(sqlTask);
            while (resultSet.next()){
                lessonInfo = new ArrayList<String>();
                lessonInfo.add("" + resultSet.getInt("number_lesson"));
                lessonInfo.add(resultSet.getString("subjects.name"));
                lessonInfo.add(resultSet.getString("type_lesson.name"));

                listOfLessons.add(lessonInfo);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Cannot get name of subjects");
        }

        return  listOfLessons;
    }

    public void setNewLab(int subject,int labNumber, String labComment, String deadlineDate, int labStatus){
        String sqlTask = " INSERT INTO labs (id, id_subject, lab_number, comment, deadline, stat)" +
                " VALUES(NULL, " + subject + ", " + labNumber + ", " + labComment + ", " + deadlineDate + ", " + labStatus + ");" ;

        try{
            statement.executeUpdate(sqlTask);
            conector.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Lab can`t be added");
        }
    }

    public ArrayList<ArrayList<String>> getAlllabsBySubject(int subject){
        ArrayList<ArrayList<String>> labsList = new ArrayList<>();
        ArrayList <String> labsInfo;

        String sqlTask = "SELECT lab_number, comment, deadline, stat FROM subjects WHERE id_subject = " + subject + ";";
        try{
            ResultSet resultSet = statement.executeQuery(sqlTask);
            while (resultSet.next()){
                labsInfo = new ArrayList<String>();
                labsInfo.add("" + resultSet.getInt("lab_number"));
                labsInfo.add(resultSet.getString("comment"));
                labsInfo.add(resultSet.getString("deadline"));
                labsInfo.add("" + resultSet.getInt("stat"));

                labsList.add(labsInfo);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Cannot get name of subjects");
        }

        return  labsList;
    }

    public ArrayList<Integer> getSubjectsForPass(String date){
        ArrayList<Integer> listOfSubjects = new ArrayList<>();

        String sqlTask = "SELECT id_subject FROM labs WHERE deadline = " + date + ";";
        try{
            ResultSet resultSet = statement.executeQuery(sqlTask);
            while(resultSet.next()) listOfSubjects.add(resultSet.getInt("id_subject"));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Not appropriate subjects");
        }

        return listOfSubjects;
    }

    public ArrayList<Integer> getNumberOfLessonsForPassedSubjects(int subject, int dayNumber, int weekType){
        ArrayList<Integer> listOfLessons = new ArrayList<>();

        String sqlTask = "SELECT number_lesson FROM timetable WHERE day_number = " + dayNumber + " AND id_subject = " + subject + " AND type_week = " + weekType + ";";
        try{
            ResultSet resultSet = statement.executeQuery(sqlTask);
            while(resultSet.next()) listOfLessons.add(resultSet.getInt("number_lessons"));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("It`s not lessons today");
        }

        return listOfLessons;
    }

}
