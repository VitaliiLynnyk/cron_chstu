package chstu.bot;

import chstu.db.DBAdapter;
import chstu.db.entity.BellTimetable;
import chstu.utils.DateUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


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
        System.out.println("-getCurrentTime: " + dateFormat.format(currentDate));
    }

    @Test
    public void convertTimeInMS() throws Exception {
        Date testDate = new Date(dateUtil.convertTimeInMS("11:20:00"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        assertEquals("Convertion is not correct","11:20:00",dateFormat.format(testDate));
        System.out.println("-convertTimeInMS: " + dateFormat.format(testDate));
    }

    @Test
    public void getTimeToNextLesson() throws Exception {
        DBAdapter dataBase = DBAdapter.getInstance();
        List<BellTimetable> bellTimetables = dataBase.getLessonTimetable();
        Boolean isMoreLesson = null;

        if (dateUtil.getTimeToNextLesson() < 0) return;

        for (BellTimetable bellTimetable : bellTimetables){
            if (dateUtil.getCurrentTimeMS() + dateUtil.getTimeToNextLesson() == dateUtil.convertTimeInMS(bellTimetable.getEndLesson())){
                System.out.println("-getTimeToNextLesson: " + bellTimetable.getEndLesson());
                return;
            }
        }

        fail("Time to next lesson not correct.");
    }

    @Test
    public void getTimeToNextDayLesson() throws Exception {
        Date testDate = new Date(dateUtil.getTimeToNextDayLesson());
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");

        System.out.println("-getTimeToNextDayLesson: " + dateFormat.format(testDate));
    }
}