package chstu.db.entity;


public class Subject {
    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
