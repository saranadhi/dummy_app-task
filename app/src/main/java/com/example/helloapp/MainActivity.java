package com.example.helloapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private String ORIGINAL_OTP="";

    EditText username;
    EditText phnNumber;

    private Button button;

    private String phn_number;
    private String user_name;

    SimpleDateFormat setFormat;
    Date time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //super.onCreate(Bundle)
        setContentView(R.layout.activity_main); //setContentView(View)

        button = (Button) findViewById(R.id.getOTP);

        username = (EditText)findViewById(R.id.username);
        phnNumber = (EditText) findViewById(R.id.phn_number);

        button.setOnClickListener(v -> {

            phn_number = phnNumber.getText().toString().trim();
            user_name = username.getText().toString().trim();

            generateOTP();
        });
    }

    private void generateOTP(){

        if(isValidNumber(phn_number)==0 && (!user_name.isEmpty())){
            Random ran = new Random();
//            String generated_otp = String.valueOf(ran.nextInt(10000));

            ORIGINAL_OTP = String.valueOf(ran.nextInt(10000));
            sendSMS();
            gotoOTPpage();

        }
        else if(user_name.isEmpty())
            Toast.makeText(MainActivity.this,"Name Can't be Empty",Toast.LENGTH_SHORT).show();

        else if(isValidNumber(phn_number)==-1)
            Toast.makeText(MainActivity.this,"Provide Exactly 10 Numbers",Toast.LENGTH_SHORT).show();

        else if(isValidNumber(phn_number)==1)
            Toast.makeText(MainActivity.this,"Invalid Number Format",Toast.LENGTH_SHORT).show();

    }

    private void sendSMS(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){

                setFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                time = new Date();
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(
                        phn_number,null,
                        setFormat.format(time)+"\nHey "+user_name+", \nYour Generated OTP : \n"+ORIGINAL_OTP,
                        null,null);

                Toast.makeText(
                        MainActivity.this,
                        "OTP send Successfully",
                        Toast.LENGTH_SHORT
                ).show();
                
            }
            else{
                requestPermissions(new String[]{android.Manifest.permission.SEND_SMS},1);
            }
        }
    }

    private void gotoOTPpage() {
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        intent.putExtra("OTPValue",ORIGINAL_OTP);
        intent.putExtra("username",user_name);
        intent.putExtra("number",phn_number);
        startActivity(intent);
    }
    private int isValidNumber(String num) {
        if(num.length()!=10)
            return -1;

        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(num);

        if(matcher.find())
            return 1;

       return 0;
    }
}