package chstu.db;

/**
 * Created by Ar-Krav on 18.05.2017.
 */
public class LessonTimetable {
    public LessonTimetable(int id, String endLesson) {
        this.id = id;
        this.endLesson = endLesson;
    }

    private int id;
    private String endLesson;

    public int getId() {
        return id;
    }

    public String getEndLesson() {
        return endLesson;
    }
}
