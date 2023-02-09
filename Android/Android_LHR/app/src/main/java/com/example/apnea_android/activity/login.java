package com.example.apnea_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apnea_android.R;
import com.example.apnea_android.info.JoinInfo;
import com.example.apnea_android.info.LoginInfo;
import com.example.apnea_android.info.MeasureRequestInfo;
import com.example.apnea_android.initMyApi;
import com.example.apnea_android.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity implements View.OnClickListener {

    Button start_button;
    Button stop_button;
    Button mainpage_button;

    private RetrofitClient retrofitClient;
    private com.example.apnea_android.initMyApi initMyApi;
    static MeasureRequestInfo measureRequestInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_start_stop);

        start_button = (Button) findViewById(R.id.start_button);
        stop_button = (Button) findViewById(R.id.stop_button);
        mainpage_button = (Button) findViewById(R.id.mainpage_button);

        start_button.setOnClickListener(this);
        stop_button.setOnClickListener(this);
        mainpage_button.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        String jsonJoinInfo = sp.getString("jsonJoinInfo", "");

        if (!jsonJoinInfo.equals("")) {
            JoinInfo joinInfo = gson.fromJson(jsonJoinInfo, JoinInfo.class);
            Log.d("kim", "username = " + joinInfo.getUsername());
            measureRequestInfo = new MeasureRequestInfo(joinInfo.getUsername(), 0);
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:     // 측정 시작 버튼
                System.out.println("측정 시작 클릭");
                if(measureRequestInfo.getStatus() == 0) {
                    statusResponse(measureRequestInfo);

                    //FCM Test
                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if(!task.isSuccessful()) {
                                        Log.d("kim", "Fatching FCM registration token failed", task.getException());
                                    }

                                    String token = task.getResult();

                                    String msg = "test" + token;
                                    Log.d("kim", msg);
//                                    Toast.makeText(login.this, msg, Toast.LENGTH_LONG).show();
                                }
                            });
                    //FCM Test
                } else if(measureRequestInfo.getStatus() == 1) {
                    Toast.makeText(login.this, "이미 측정중 입니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.stop_button:    // 측정 종료 버튼
                System.out.println("측정 종료 클릭");
                if(measureRequestInfo.getStatus() == 1) {
                    statusResponse(measureRequestInfo);
                } else if(measureRequestInfo.getStatus() == 0) {
                    Toast.makeText(login.this, "이미 측정 종료 상태 입니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mainpage_button:
                if(measureRequestInfo.getStatus() == 1){
                    Toast.makeText(login.this, "측정 중입니다. 메인페이지로 가시려면\n          측정 종료를 눌러주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(measureRequestInfo.getStatus() == 0) {
                    System.out.println("메인페이지 클릭");
                    Toast.makeText(login.this, "로그아웃 - 메인페이지 미구현", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

        }
    }

    public void statusResponse(MeasureRequestInfo statusUser) {
        //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();


        //User에 저장된 데이터와 함께 init에서 정의한 getJoinResponse 함수를 실행한 후 응답을 받음
        initMyApi.getStatusResponse(statusUser).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("kim", "retrofit Data Fetch success");
                Log.d("kim", "joinResponse: " + statusUser.toString());
                //통신 성공
                if (response.isSuccessful()) {
                    Log.d("kim", response.body().toString());
                    if (response.body() == 1) {
                        Toast.makeText(login.this, "측정 시작", Toast.LENGTH_SHORT).show();
                    } else if (response.body() == 0) {
                        Toast.makeText(login.this, "측정 종료", Toast.LENGTH_SHORT).show();
                    } else if (response.body() == -1) {
                        Toast.makeText(login.this, "? 오류 발생 ?", Toast.LENGTH_SHORT).show();
                    }
                    statusUser.setStatus(response.body());
                    //preference status 값 최신화
                    saveJoinInfo(statusUser);
                } else {
                    Toast.makeText(login.this, "0.통신 오류로 인한 측정 시작 실패", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(login.this, "1.통신 오류로 인한 측정 시작 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //sharedPreference에 MeasureRequestInfo 저장
    public void saveJoinInfo(MeasureRequestInfo measureRequestInfo) {
        Gson gson = new GsonBuilder().create();
        SharedPreferences sp;

        sp = getSharedPreferences("shared", MODE_PRIVATE);

        String jsonMeasureRequestInfo = gson.toJson(measureRequestInfo, MeasureRequestInfo.class);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("jsonMeasureRequestInfo", jsonMeasureRequestInfo);
        editor.commit();
    }
}