package chstu.db.entity;

public class Laboratory {
    public Laboratory(int id, int idSubject, int labNumber, String comment, String deadline, int status) {
        this.id = id;
        this.idSubject = idSubject;
        this.labNumber = labNumber;
        this.comment = comment;
        this.deadline = deadline;
        this.status = status;
    }

    private int id;
    private int idSubject;
    private int labNumber;
    private String comment;
    private String deadline;
    private int status;

    // todo good practice - constructor should be here, after fields, same for other classes

    public int getId() {
        return id;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public int getLabNumber() {
        return labNumber;
    }

    public String getComment() {
        return comment;
    }

    public String getDeadline() {
        return deadline;
    }

    public int getStatus() {
        return status;
    }
}
