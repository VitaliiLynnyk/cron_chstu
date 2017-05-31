package chstu.db.entity;

/**
 * Created by Ar-Krav on 18.05.2017.
 */
public class BellTimetable {
    private int id;
    private String endLesson;

    public BellTimetable(int id, String endLesson) {
        this.id = id;
        this.endLesson = endLesson;
    }

    public int getId() {
        return id;
    }

    public String getEndLesson() {
        return endLesson;
    }
}
