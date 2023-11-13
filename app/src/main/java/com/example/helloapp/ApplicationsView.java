package com.example.helloapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationsView extends AppCompatActivity {

    ImageView yt;

    ImageView ticket;

    Button logout;

    private String user_name;
    private String phn_number;
    SimpleDateFormat setFormat;
    Date time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications_view);

        user_name = getIntent().getStringExtra("username");
        phn_number = getIntent().getStringExtra("phnnumber");
        yt = (ImageView) findViewById(R.id.youtube);

        yt.setOnClickListener(v -> {
            Uri open = Uri.parse("https://www.youtube.com/");
            startActivity(new Intent(Intent.ACTION_VIEW,open));
        });

        ticket = (ImageView) findViewById(R.id.ticket);
        ticket.setOnClickListener(v -> {
            Uri open = Uri.parse("https://www.irctc.co.in/nget/train-search");
            startActivity(new Intent(Intent.ACTION_VIEW,open));
        });

        logoutApp();
    }


    @Override
    public void onBackPressed() {
        /*
        Alert Message
         */
        Intent backTOLogin = new Intent(ApplicationsView.this,MainActivity.class);
        Toast.makeText(
                ApplicationsView.this,
                "You are out of the application",
                Toast.LENGTH_SHORT).show();
        startActivity(backTOLogin);
    }

    private void logoutApp(){
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            Intent backTOLogin = new Intent(ApplicationsView.this,MainActivity.class);
            startActivity(backTOLogin);
            logoutSMS();
        });
    }

    private void logoutSMS() {
        SmsManager manager = SmsManager.getDefault();
        setFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        time = new Date();
        manager.sendTextMessage(phn_number,null,
                setFormat.format(time)+"\nHi "+user_name+",\nYour Successfully Logged out :)",
                null,null);
    }

}