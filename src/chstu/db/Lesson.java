package chstu.db;


public class Lesson {
    public Lesson(int number, String name, String type) {
        this.number = number;
        this.name = name;
        this.type = type;
    }

    private int number;
    private String name;
    private String type;

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
