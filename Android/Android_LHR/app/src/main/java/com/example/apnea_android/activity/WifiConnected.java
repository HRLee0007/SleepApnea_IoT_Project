package com.example.apnea_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apnea_android.R;

public class WifiConnected extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(WifiConnected.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
