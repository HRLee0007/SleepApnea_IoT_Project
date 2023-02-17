package com.example.apnea_android.activity;

import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class WifiConnected extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                Intent intent = new Intent(this, MeasureControlActivity.class); //화면 전환
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
//                finish();
//            }
//        }, 500); //딜레이 타임 조절

    }
}
