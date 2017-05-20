package chstu.timetable;

import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Ar-Krav on 20.05.2017.
 */
public class DateUtilTest {

    static DateUtil dateUtil;

    @BeforeClass
    public static void createDateUtil(){
        dateUtil = new DateUtil();
    }

    @Test
    public void getCurrentTime() throws Exception {
        Date currentDate = new Date();
        Date testDate = new Date(dateUtil.getCurrentTimeMS());

        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");
        assertEquals("Time is diferent!",dateFormat.format(currentDate),dateFormat.format(testDate));
        System.out.println(dateFormat.format(currentDate));
    }

    @Test
    public void convertTimeInMS() throws Exception {
        Date testDate = new Date(dateUtil.convertTimeInMS("11:20:00"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        assertEquals("Convertion is not correct","11:20:00",dateFormat.format(testDate));
        System.out.println(dateFormat.format(testDate));
    }

    @Test
    public void getTimeToNextLesson() throws Exception {

    }

}