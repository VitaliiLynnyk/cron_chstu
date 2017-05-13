package chstu;

/**
 * Created by alex3 on 5/13/2017.
 */
import java.sql.*;

public class SQLiteJDBC
{
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chdtu.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE subjects" +
                    "(ID INT(10) PRIMARY KEY     NOT NULL," +
                    " name varchar(50) NOT NULL)" ;
            stmt.executeUpdate(sql);
            sql = "create table type_tasks"+
                    "(ID INT(10) PRIMARY KEY     NOT NULL,"+
                    " name varchar(50) NOT NULL)" ;
            stmt.executeUpdate(sql);
            sql = "create table type_lesson"+
                    "( INT(10) PRIMARY KEY     NOT NULL,"+
                    " name varchar(50) NOT NULL)" ;
            stmt.executeUpdate(sql);
            sql = "create table depot"+
                    "(id INT(10) PRIMARY KEY     NOT NULL,"+
                    "number_lesson INT(10) NOT NULL,"+
                    "id_lesson int(10) NOT NULL, "+
                    "day int(1) NOT NULL, "+
                    "type_week boolean,"+
                    "type_lesson int(10))"
                    ;
            stmt.executeUpdate(sql);
            sql = "create table tasks"+
                    "(id INT(10) PRIMARY KEY     NOT NULL,"+
                    "number_task int(10) not null,"+
                    "id_subject int(10) not null,"+
                    "date_delivery varchar(10) not null"+
                    "type_task int(10))";
            stmt.executeUpdate(sql);
            sql = "create table result"+
                    "(id INT(10) PRIMARY KEY     NOT NULL,"+
                    "id_task int(10) not null,"+
                    "is_done boolean )";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}