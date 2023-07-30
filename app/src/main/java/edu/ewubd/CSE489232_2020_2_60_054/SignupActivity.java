package edu.ewubd.CSE489232_2020_2_60_054;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends Activity {

    private Button goBtn, exitBtn;

    private EditText suName, suEmail, suPhone, suUser, suPass, suConPass;

    private TextView loginRedirect, account, title;

    private CheckBox reUser, rePass;

    private TableRow rowName, rowPhone, rowEmail, rowRepass;
    LinearLayout hideLogin;

    private boolean isLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        goBtn = findViewById(R.id.goBtn);
        exitBtn = findViewById(R.id.exitBtn);

        suName = findViewById(R.id.suName);
        suEmail = findViewById(R.id.suEmail);
        suPhone = findViewById(R.id.suPhone);
        suUser = findViewById(R.id.suUser);
        suPass = findViewById(R.id.suPass);
        suConPass = findViewById(R.id.suConPass);

        reUser = findViewById(R.id.chUser);
        rePass = findViewById(R.id.chPass);

        rowName = findViewById(R.id.rowName);
        rowPhone = findViewById(R.id.rowPhone);
        rowEmail = findViewById(R.id.rowEmail);
        rowRepass = findViewById(R.id.rowRePass);

        account = findViewById(R.id.account);
        loginRedirect = findViewById(R.id.loginRedirect);
        title = findViewById(R.id.title);

        this.changeView();

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String err = "";
                String name = suName.getText().toString();
                String email = suEmail.getText().toString();
                String phone = suPhone.getText().toString();
                String userid = suUser.getText().toString();
                String pass = suPass.getText().toString();
                String con_pass = suConPass.getText().toString();

                if(!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !userid.isEmpty() && !pass.isEmpty() && !con_pass.isEmpty() && !isLogin){
                    if(name.length() < 4 || name.length() > 12 || !name.matches("^[a-zA-Z ]+$")){
                        err += "Invalid Name (4-12 long and only alphabets)\n";
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
                    //userid
                    if(userid.length() < 4 || !userid.matches("^[a-zA-Z0-9 ]+$")){
                        err += "Invalid userid (4-12 long and only alphabets and number)\n";
                    }

                    if(pass.length() < 8 && !pass.equals(con_pass)){
                        err += "Password should at least 8 char or Check your password\n";
                    }

                    //checkbox
                    boolean chUser = reUser.isChecked();
                    boolean chPass = rePass.isChecked();

//                    if(chUser){
//                        //save userid
//                    }
//
//                    if(chPass){
//                        //save user password
//                    }
                }
                else if (!userid.isEmpty() && !pass.isEmpty() && isLogin) {
                    //userid
                    if(userid.length() < 4 || !userid.matches("^[a-zA-Z0-9 ]+$")){
                        err += "Invalid userid (4-12 long and only alphabets and number)\n";
                    }

                    if(pass.length() < 8 && !pass.equals(con_pass)){
                        err += "Password should at least 8 char or Check your password\n";
                    }
                }
                else {
                    err += "Please all the field\n";
                }


                if(err.length()>0){
                    showErrorDialog(err);
                }
                else{
                    Intent i = new Intent(SignupActivity.this, MainActivity.class);
                    //signup data
                    startActivity(i);
                }
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin){
                    isLogin = false;
                }
                else {
                    isLogin = true;
                }
                changeView();

            }
        });


    }

    private void changeView(){
        if(isLogin){
            rowName.setVisibility(View.GONE);
            rowEmail.setVisibility(View.GONE);
            rowPhone.setVisibility(View.GONE);
            rowRepass.setVisibility(View.GONE);
            account.setText("Don't have an account?");
            loginRedirect.setText("Sign Up");
            title.setText("Login");
        }
        else{
            rowName.setVisibility(View.VISIBLE);
            rowEmail.setVisibility(View.VISIBLE);
            rowPhone.setVisibility(View.VISIBLE);
            rowRepass.setVisibility(View.VISIBLE);
            account.setText("Already have an account?");
            loginRedirect.setText("Login");
            title.setText("Signup");
        }
    }

    private void showErrorDialog(String errMsg){
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