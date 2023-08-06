package edu.ewubd.CSE489232_2020_2_60_054;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView tv = findViewById(R.id.show);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // runtime call the abstract method
        //modify after that
        setContentView(R.layout.main_activity); // resource.layout_directory.activityxml_file_name
        loadEvents();
    }

    private void loadEvents(){
        String q = "SELECT * FROM events";
        EventDB db = new EventDB(this);
        Cursor cur = db.selectEvents(q);
        if(cur != null){
            if(cur.getCount() > 0){ // number of rows
                while (cur.moveToNext()){
                    String ID = cur.getString(0);
                    String name = cur.getString(1);
                    String place = cur.getString(2);
                    long _date = cur.getLong(3);
                    int _capacity = cur.getInt(4);
                    double _budget = cur.getDouble(5);
                    String email = cur.getString(6);
                    String phone = cur.getString(7);
                    String desc = cur.getString(8);

                    //todo line by line show comma separated value of the table rows

                    String csvRow = ID + "," + name + "," + place + "," + _date + "," + _capacity + "," + _budget + "," + email + "," + phone + "," + desc;
                    System.out.println(csvRow);

                    //demo row tv print
                    Log.d("rowOutput", csvRow);
                }
            }
            cur.close();
        }
        db.close();
    }
}