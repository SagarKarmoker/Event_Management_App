package edu.ewubd.CSE489232_2020_2_60_054;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // runtime call the abstract method
        //modify after that
        setContentView(R.layout.main_activity); // resource.layout_directory.activityxml_file_name
    }
}