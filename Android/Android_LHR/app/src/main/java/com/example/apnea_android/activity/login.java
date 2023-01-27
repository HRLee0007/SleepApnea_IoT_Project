package com.example.apnea_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apnea_android.R;
import com.example.apnea_android.info.LoginInfo;
import com.example.apnea_android.info.MeasureRequestInfo;
import com.example.apnea_android.initMyApi;
import com.example.apnea_android.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity implements View.OnClickListener {

    Button start_button;
    Button stop_button;
    private RetrofitClient retrofitClient;
    private com.example.apnea_android.initMyApi initMyApi;
    MeasureRequestInfo usr1 = new MeasureRequestInfo("gusfh", 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_start_stop);

        start_button = (Button) findViewById(R.id.start_button);
        stop_button = (Button) findViewById(R.id.stop_button);

        start_button.setOnClickListener(this);
        stop_button.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:     // 측정 시작 버튼
                System.out.println("측정 시작 클릭");
                if(usr1.getStatus() == 0) {
                    statusResponse(usr1);
                } else if(usr1.getStatus() == 1) {
                    Toast.makeText(login.this, "이미 측정중 입니다.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.stop_button:    // 측정 종료 버튼
                System.out.println("측정 종료 클릭");
                if(usr1.getStatus() == 1) {
                    statusResponse(usr1);
                } else if(usr1.getStatus() == 0) {
                    Toast.makeText(login.this, "이미 측정 종료 상태 입니다.", Toast.LENGTH_LONG).show();
                }
                break;
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
                        Toast.makeText(login.this, "측정 시작", Toast.LENGTH_LONG).show();
                    } else if (response.body() == 0) {
                        Toast.makeText(login.this, "측정 종료", Toast.LENGTH_LONG).show();
                    } else if (response.body() == -1) {
                        Toast.makeText(login.this, "??? 뭐함 ㅋ", Toast.LENGTH_LONG).show();
                    }
                    statusUser.setStatus(response.body());
                } else {
                    Toast.makeText(login.this, "0.통신 오류로 인한 측정 시작 실패", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(login.this, "1.통신 오류로 인한 측정 시작 실패", Toast.LENGTH_LONG).show();
            }
        });
    }
}