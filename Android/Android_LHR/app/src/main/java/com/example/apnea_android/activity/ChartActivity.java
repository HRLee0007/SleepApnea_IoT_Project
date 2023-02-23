package com.example.apnea_android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apnea_android.R;
import com.example.apnea_android.RetrofitClient;
import com.example.apnea_android.info.CountInfo;
import com.example.apnea_android.info.JoinInfo;
import com.example.apnea_android.info.MeasureRequestInfo;
import com.example.apnea_android.initMyApi;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener{

    private RetrofitClient retrofitClient;
    private com.example.apnea_android.initMyApi initMyApi;
    static JoinInfo joinInfo;
    static List<CountInfo> userCountInfo;

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

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        String jsonJoinInfo = sp.getString("jsonJoinInfo", "");

        if (!jsonJoinInfo.equals("")) {
            joinInfo = gson.fromJson(jsonJoinInfo, JoinInfo.class);
            Log.d("kim", "ChartActivity username = " + joinInfo.getUsername());
        }

        //무호흡 측정 현황 리스트 받기
        findCountResponse(joinInfo.getUsername());

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

    public void findCountResponse(String username) {
        //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();



        //User에 저장된 데이터와 함께 init에서 정의한 getJoinResponse 함수를 실행한 후 응답을 받음
        initMyApi.getUserCount(username).enqueue(new Callback<List<CountInfo>>() {
            @Override
            public void onResponse(Call<List<CountInfo>> call, Response<List<CountInfo>> response) {
                Log.d("kim", "retrofit Data Fetch success");
                Log.d("kim", "findCountResponse username: " + username);

                //통신 성공
                if (response.isSuccessful() && response.body() != null) {

                    userCountInfo = response.body();

                    for (int i = 0; i < userCountInfo.size(); i++){
                        Log.d("kim", "findCountResponse count: " + userCountInfo.get(i).getCount());
                    }

                } else {
                    Toast.makeText(ChartActivity.this, "예기치 못한 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<CountInfo>> call, Throwable t) {
                Toast.makeText(ChartActivity.this, "통신이 불안정 합니다", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("kim", "findCountResponse end");

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