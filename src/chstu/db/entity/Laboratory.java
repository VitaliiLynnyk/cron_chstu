package chstu.db.entity;

public class Laboratory {
    private int id;
    private int idSubject;
    private int labNumber;
    private String comment;
    private String deadline;
    private int status;

    public Laboratory(int id, int idSubject, int labNumber, String comment, String deadline, int status) {
        this.id = id;
        this.idSubject = idSubject;
        this.labNumber = labNumber;
        this.comment = comment;
        this.deadline = deadline;
        this.status = status;
    }

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
