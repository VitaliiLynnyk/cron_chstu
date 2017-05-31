package chstu.db.entity;


public class LessonTimetable {
    private int id;
    private int numberLesson;
    private int idSubject;
    private String lessonDate;
    private int typeLesson;

    public LessonTimetable(int id, int numberLesson, int idSubject, String lessonDate, int typeLesson) {
        this.id = id;
        this.numberLesson = numberLesson;
        this.idSubject = idSubject;
        this.lessonDate = lessonDate;
        this.typeLesson = typeLesson;
    }

    public int getId() {
        return id;
    }

    public int getNumberLesson() {
        return numberLesson;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public int getTypeLesson() {
        return typeLesson;
    }
}
