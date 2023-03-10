package com.JounCamp.apnea_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.JounCamp.apnea_android.R;
import com.JounCamp.apnea_android.RetrofitClient;
import com.JounCamp.apnea_android.info.JoinInfo;
import com.JounCamp.apnea_android.info.LoginInfo;
import com.JounCamp.apnea_android.info.MeasureRequestInfo;
import com.JounCamp.apnea_android.info.ResponseInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button join_btn;    //회원가입 버튼
    Button login_btn;   //로그인 버튼
    String token;
    EditText id_edit;

    private RetrofitClient retrofitClient;
    private com.JounCamp.apnea_android.initMyApi initMyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        join_btn = (Button)findViewById(R.id.register_button);    // 회원가입 버튼을 찾고
        login_btn = (Button)findViewById(R.id.login_button1);  // 로그인 버튼을 찾고

        join_btn.setOnClickListener(this);                 // 리스너를 달아줌.
        login_btn.setOnClickListener(this);                // 리스너를 달아줌.

        //토큰 값 보기
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("Kim", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        token = task.getResult();

                        // Log and toast
                        Log.d("Kim", "token = " + token);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:     // 회원가입 버튼을 눌렀을 때
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);  // 새 액티비티를 열어준다.
                break;
            case R.id.login_button1:    // 로그인 버튼을 눌렀을 때
                LoginInfo loginUser;

                id_edit = findViewById(R.id.login_username);    // id 에디트를 찾음.
                EditText pw_edit = findViewById(R.id.login_password);    // pw 에디트를 찾음

                loginUser = new LoginInfo(id_edit.getText().toString(), pw_edit.getText().toString(), token);

                loginResponse(loginUser);
                break;
        }
    }

    public void loginResponse(LoginInfo loginUser) {
        //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();

        //User에 저장된 데이터와 함께 init에서 정의한 getJoinResponse 함수를 실행한 후 응답을 받음
        initMyApi.getLoginResponse(loginUser).enqueue(new Callback<ResponseInfo<JoinInfo>>() {
            @Override
            public void onResponse(Call<ResponseInfo<JoinInfo>> call, Response<ResponseInfo<JoinInfo>> response) {
                Log.d("kim", "retrofit Data Fetch success");
                Log.d("kim", "loginResponse: " + loginUser.toString());

                //통신 성공
                if (response.isSuccessful() && response.body() != null) {
                    ResponseInfo<JoinInfo> result = response.body();

                    if(result.getStatus() == 200) {
                        Toast.makeText(MainActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();

                        //프리퍼런스에 reuslt.getData로 유저 정보 저장하기 구현
                        JoinInfo joinInfo = new JoinInfo(result.getData().getUsername(), result.getData().getPassword(),
                                result.getData().getRealname(), result.getData().getEmail(), result.getData().getAddress(),
                                result.getData().getPhoneNum(), result.getData().getC_phoneNum(), result.getData().getRole());

                        saveJoinInfo(joinInfo);

                        Intent intent = new Intent(MainActivity.this, ConnectWifiActivity.class);
                        intent.putExtra("username", id_edit.getText().toString());
                        startActivity(intent);
                    } else if(result.getStatus() == 400) {
                        Toast.makeText(MainActivity.this,
                                "아이디 혹은 비밀번호를 다시 학인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "예기치 못한 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseInfo<JoinInfo>> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "통신이 불안정 합니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //sharedPreference에 유저 정보 저장 + measureRequestInfo 저장
    public void saveJoinInfo(JoinInfo joinInfo) {
        Gson gson = new GsonBuilder().create();
        SharedPreferences sp;

        sp = getSharedPreferences("shared", MODE_PRIVATE);

        String jsonJoinInfo = gson.toJson(joinInfo, JoinInfo.class);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("jsonJoinInfo", jsonJoinInfo);
        editor.commit();

        //MeasureRequestInfo 프리퍼런스에 저장, username이랑 status
        MeasureRequestInfo measureRequestInfo = new MeasureRequestInfo(joinInfo.getUsername(), 0);
        String jsonMeasureRequestInfo = gson.toJson(measureRequestInfo, MeasureRequestInfo.class);
        
        editor.putString("jsonMeasureRequestInfo", jsonMeasureRequestInfo);
        editor.commit();
    }
}