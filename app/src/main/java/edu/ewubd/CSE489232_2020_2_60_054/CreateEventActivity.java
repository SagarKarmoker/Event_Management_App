package edu.ewubd.CSE489232_2020_2_60_054;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CreateEventActivity extends Activity {

    // R java generated Resource file
    private EditText etName, etPlace, etDate, etCapacity, etBudget, etEmail, etPhone, etDsc;
    private Button cancelBtn, shareBtn, saveBtn;
    private TextView errorTv;
    private RadioGroup radioGroup;
    private String radioText = "";
    // error text
    String err = "";

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

        // Radio
        radioGroup = findViewById(R.id.radioGroup);


        //etName.getText() --> return editable object
        String name = etName.getText().toString();
        String capacity = etCapacity.getText().toString();
        String place = etPlace.getText().toString();
        String date = etDate.getText().toString();
        String budget = etBudget.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String desc = etDsc.getText().toString();

        // converting string to Integer
        int cap, event_budget;
        long phn;

        if(!capacity.isEmpty() && !budget.isEmpty() && !phone.isEmpty()){
            cap = Integer.parseInt(capacity);
            event_budget = Integer.parseInt(budget);
            phn = Long.parseLong(phone);

            if(cap <= 0 || cap > 1000 && event_budget > 0){
                err += "Invalid capacity\n or invalid budget";
            }
        }


        // error text show
        if(!err.isEmpty()){
            errorTv.setText(err);
            return;
        }
        
        // radio button
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioIndoor){
                    radioText += "Indoor";
                }
                else if(checkedId == R.id.radioOutdoor){
                    radioText += "Outdoor";
                }
                else if (checkedId == R.id.radioOnline) {
                    radioText += "Online";
                }
            }
        });


        // button works
        /*
        * physical event convert into software event
        * // 1. retrieve all field data from XML
                // 2. check the validity of data
                    // a. if not valid, then show error message
                    // b. if everything ok, then save
        * */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Save Button was clicked");

                // input field check
                if(place.isEmpty() || date.isEmpty() || budget.isEmpty() || email.isEmpty() || phone.isEmpty() || desc.isEmpty()){
                    if(name.isEmpty() || name.length() < 4 || name.length() > 12){
                        err += "Invalid Name\n";
                    }

                    err += "please fill all the fields";
                }


            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("cancel btn");
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, "event details");
                i.setType("text/plain");

                startActivity(i);
            }
        });
        // end of on create
    }

    // text input valid check
    public void inputCheck(){

    }
}