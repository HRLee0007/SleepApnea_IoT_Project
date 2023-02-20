package com.example.apnea_android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apnea_android.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener{

    //선 그래프
    private BarChart barChart;

    Button mainpage_button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_page);

        ArrayList<BarEntry> entry_chart = new ArrayList<>(); // 데이터를 담을 Arraylist

        barChart = (BarChart) findViewById(R.id.chart);
        mainpage_button = (Button) findViewById(R.id.mainpage_button);

        mainpage_button.setOnClickListener(this);

        BarData barData = new BarData(); // 차트에 담길 데이터

        entry_chart.add(new BarEntry(1, 1)); //entry_chart1에 좌표 데이터를 담는다.
        entry_chart.add(new BarEntry(2, 2));
        entry_chart.add(new BarEntry(3, 3));
        entry_chart.add(new BarEntry(4, 4));
        entry_chart.add(new BarEntry(5, 2));
        entry_chart.add(new BarEntry(6, 8));
        entry_chart.add(new BarEntry(7, 12));


        BarDataSet barDataSet = new BarDataSet(entry_chart, "무호흡 횟수"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.

        barDataSet.setColor(Color.BLUE); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.

        barData.addDataSet(barDataSet); // 해당 BarDataSet 을 적용될 차트에 들어갈 DataSet 에 넣는다.

        barChart.setData(barData); // 차트에 위의 DataSet 을 넣는다.

        barChart.invalidate(); // 차트 업데이트
//        barChart.setTouchEnabled(false); // 차트 터치 불가능하게

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mainpage_button:

                Toast.makeText(ChartActivity.this, "로그아웃 - 메인페이지 미구현", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChartActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

        }
    }
}