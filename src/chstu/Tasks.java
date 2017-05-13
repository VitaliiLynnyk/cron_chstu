package chstu;

import chstu.db.DBAdapter;

/**
 * Created by alex3 on 5/13/2017.
 */
public class Tasks {
    final public String StartSemester = "2017-05-15";
    final public String EndSemester = "2017-05-29";
    DBAdapter dbAdapter = new DBAdapter();
    int numberLessons(int i){

        for(int j=1; j<3;j++) {
            for (int i = 1; i < 6; i++) {
                if(dbAdapter.getAllLessonsOfDay(i,j);
            }
        }
    }

}
