package com.rocketapp.utkansh20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QRScan_Result extends AppCompatActivity {

    TextView name_tv,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan__result);
        name_tv = findViewById(R.id.name);
        phone = findViewById(R.id.phoneNumber);
       // String name = getIntent().getStringExtra("name");
       String phoneNumber = getIntent().getStringExtra("number");
        //System.out.println(name+"--------------------------------"+phoneNumber);
        //if(name!=null)
        //name_tv.setText(name);
        if(phoneNumber!=null)
             phone.setText(phoneNumber);
    }
}