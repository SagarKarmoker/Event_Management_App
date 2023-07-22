package edu.ewubd.CSE489232_2020_2_60_054;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateEventActivity extends Activity {

    // R java generated Resource file

    private EditText etName, etPlace, etDate, etCapacity, etBudget, etEmail, etPhone, etDsc;
    private Button cancelBtn, shareBtn, saveBtn;
    private TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // edittext fields
        etName = findViewById(R.id.etName);
        etPlace= findViewById(R.id.etPlace);
        etDate = findViewById(R.id.etDate);
        etCapacity = findViewById(R.id.etCapacity);
        etBudget = findViewById(R.id.etBudget);
        etEmail= findViewById(R.id.etEmail);
        etPhone= findViewById(R.id.etPhone);
        etDsc = findViewById(R.id.etDsc);

        // buttons
        cancelBtn = findViewById(R.id.cancelBtn);
        shareBtn = findViewById(R.id.shareBtn);
        saveBtn = findViewById(R.id.saveBtn);

        // textviews
        errorTv = findViewById(R.id.errorTv);


        //etName.getText() --> return editable object
        String name = etName.getText().toString();
        String capacity = etCapacity.getText().toString();
        String place = etPlace.getText().toString();
        String date = etDate.getText().toString();
        String budget = etBudget.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String desc = etDsc.getText().toString();

        String err = "";

        if(name.isEmpty() || name.length() < 4 || name.length() > 12){
            err += "Invalid Name\n";
        }

        int cap = Integer.parseInt(capacity);
        if(cap <= 0 || cap > 1000){
            err += "Invaild capacity\n";
        }

        if(!err.isEmpty()){
            errorTv.setText(err);
            return;
        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // physical event convert into software event
                System.out.println("Save Button was clicked");
                // 1. retrieve all field data from XML
                // 2. check the validity of data
                    // a. if not valid, then show error message
                    // b. if everything ok, then save
            }
        });



        // end of oncreate

    }
}