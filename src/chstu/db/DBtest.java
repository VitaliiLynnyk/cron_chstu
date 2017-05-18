package chstu.db;


public class DBtest {
    public static void main(String[] args) {
        DBAdapter db = DBAdapter.getInstance();

        db.setNewLab(3,1,7,"test new lab","2017-05-14",1);
        System.out.println("All good11");
    }
}
