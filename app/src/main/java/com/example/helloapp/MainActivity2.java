package com.example.helloapp;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private Button verifyOTP;

    private String ORIGINAL_OTP;
    private TextView infoPanel;

    private Button resend;

    private EditText userInput;
    String user_name;
    String number;

    SimpleDateFormat setFormat;
    Date time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ORIGINAL_OTP = getIntent().getStringExtra("OTPValue");

        user_name = getIntent().getStringExtra("username");
        number= getIntent().getStringExtra("number");

        setInformation();

        verifyOTP = (Button)findViewById(R.id.valid);

        verifyOTP.setOnClickListener(v -> {
            userInput = (EditText) findViewById(R.id.userOTP);
            String VALUE = userInput.getText().toString();

            validateOTP(VALUE);
        });
        resendOTP();
    }

    private void validateOTP(String value) {
        if(ORIGINAL_OTP.equals(value)){
            Toast.makeText(
                    MainActivity2.this,
                    "Welcome to Demo_App \nYour Successfully Logged in",
                    Toast.LENGTH_SHORT
            ).show();

            sendStatus();
            openApplications();
        }
        else{
            Toast.makeText(
                    MainActivity2.this,
                    "Incorrect OTP \nKindly Provide a Correct OTP",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void sendStatus() {
        SmsManager manager = SmsManager.getDefault();
        setFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        time = new Date();
        manager.sendTextMessage(number,null,
                setFormat.format(time)+"\nHi "+user_name+",\nYour Successfully Logged in.",
                null,null);
    }
    private void openApplications() {
        Intent openApp = new Intent(MainActivity2.this, ApplicationsView.class);
        openApp.putExtra("username",user_name);
        openApp.putExtra("phnnumber",number);
        startActivity(openApp);
    }


    private void setInformation() {
        infoPanel = (TextView) findViewById(R.id.putInfo);
        infoPanel.setText("Hi " +user_name+", \nWe have send you an OTP \nKindly check your messege inbox");
    }

    private void resendOTP(){

        resend = (Button)findViewById(R.id.resendOTP);
        resend.setOnClickListener(v -> {

            userInput.setText("");
            Random ran = new Random();

            ORIGINAL_OTP = String.valueOf(ran.nextInt(10000));
            SmsManager manager = SmsManager.getDefault();


            setFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            time = new Date();
            manager.sendTextMessage(number,null,
                    setFormat.format(time)+"\nHey "+user_name+",\nHere is Your Re-generated OTP : \n"+ORIGINAL_OTP,
                    null,null);

            Toast.makeText(
                    MainActivity2.this,
                    "OTP re-send successfully",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}