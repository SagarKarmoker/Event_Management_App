package edu.ewubd.CSE489232_2020_2_60_054;
// Sagar Karmoker
// ID:2020-2-60-054

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateEventActivity extends AppCompatActivity {

    // R java generated Resource file
    EditText etName, etPlace, etDate, etCapacity, etBudget, etEmail, etPhone, etDsc;
    Button cancelBtn, shareBtn, saveBtn;
    TextView errorTv;
    RadioGroup radioGroup;

    RadioButton rIndoor, rOutdoor, rOnline;
    // error text


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
                //radio button
                rIndoor = findViewById(R.id.radioIndoor);
                rOutdoor = findViewById(R.id.radioOutdoor);
                rOnline = findViewById(R.id.radioOnline);

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

                // input field check
                if(!name.isEmpty() && !place.isEmpty() && !date.isEmpty() && !capacity.isEmpty() && !budget.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !desc.isEmpty()){
                    //name
                    if(name.length() < 4 || name.length() > 12 || !name.matches("^[a-zA-Z ]+$")){
                        err += "Invalid Name (4-12 long and only alphabets)\n";
                    }

                    //place
                    if(place.length() >= 6 && place.length() <= 64 && !place.matches("^[a-zA-Z0-9, ]+$")){
                        err += "Invalid Place (only alpha-numeric and , and 6-64 characters)\n";
                    }

                    // radio button check
                    boolean isIndoor = rIndoor.isChecked();
                    boolean isOutdoor = rOutdoor.isChecked();
                    boolean isOnline = rOnline.isChecked();

                    if(!isIndoor && !isOutdoor && !isOnline){
                        err += "Please select event type\n";
                    }

                    // converting string to Integer
                    int cap;
                    double event_budget;

                    cap = Integer.parseInt(capacity);
                    event_budget = Double.parseDouble(budget);

                    if(cap <= 0){
                        err += "Invalid capacity (number greater than zero)\n";
                    }


                    if(event_budget < 1000){
                        err += "Invalid budget (number greater than 1000.00)\n";
                    }

                    // Date and time checking
                    String format = "yyyy-MM-dd HH:mm";
                    Date checkDate = null;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        checkDate = sdf.parse(date);
                        if (!date.equals(sdf.format(checkDate))) {
                            checkDate = null;
                        }
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    if (checkDate == null) {
                        // Invalid date format
                        err += "Invalid date format (yyyy-MM-dd HH:mm)\n";
                    }

                    //email
                    String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                    if(!email.matches(EMAIL_REGEX)){
                        err += "Invalid email format\n";
                    }

                    //phone
                    Pattern pattern = Pattern.compile("^\\+\\d{13}$");
                    Matcher matcher = pattern.matcher(phone);
                    if(!matcher.matches()){
                        err += "Invalid phone number (format +8801234556789)\n";
                    }

                    //description
                    Log.d("desc", String.valueOf(desc.length()));
                    if(desc.length() < 10 || desc.length() > 1000){
                        err += "Invalid description format (10-1000 characters)\n";
                    }
                }
                else{
                    err += "Fill all the fields\n";
                }

                //dialog
                if(err.length() > 0){
                    showErrorDialog(err);
                    // error text show
                    errorTv.setText(err);
                }else{
                    errorTv.setText("");
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

    public void showErrorDialog(String errMsg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(errMsg);
        builder.setCancelable(true);

        builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}