package chstu.db.entity;


public class Lesson {
    private int number;
    private String name;
    private String type;

    public Lesson(int number, String name, String type) {
        this.number = number;
        this.name = name;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
