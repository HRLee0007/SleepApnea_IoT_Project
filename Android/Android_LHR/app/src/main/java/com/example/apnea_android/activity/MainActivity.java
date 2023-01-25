package com.example.apnea_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apnea_android.R;
import com.example.apnea_android.RetrofitClient;
import com.example.apnea_android.info.LoginInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button join_btn;    //회원가입 버튼
    Button login_btn;   //로그인 버튼

    EditText id_edit;                // id 에디트
    EditText pw_edit;                // pw 에디트

    private RetrofitClient retrofitClient;
    private com.example.apnea_android.initMyApi initMyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        join_btn = (Button)findViewById(R.id.register_button);    // 회원가입 버튼을 찾고
        login_btn = (Button)findViewById(R.id.login_button1);  // 로그인 버튼을 찾고

        join_btn.setOnClickListener(this);                 // 리스너를 달아줌.
        login_btn.setOnClickListener(this);                // 리스너를 달아줌.
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:     // 회원가입 버튼을 눌렀을 때
                Intent intent = new Intent(MainActivity.this, join.class);
                startActivity(intent);  // 새 액티비티를 열어준다.
                break;
            case R.id.login_button1:    // 로그인 버튼을 눌렀을 때
                LoginInfo loginUser;

                id_edit = (EditText)findViewById(R.id.login_username);    // id 에디트를 찾음.
                pw_edit = (EditText)findViewById(R.id.login_password);    // pw 에디트를 찾음

                loginUser = new LoginInfo(id_edit.toString(), pw_edit.toString());

                loginResponse(loginUser);
                break;
        }
    }

    public void loginResponse(LoginInfo loginUser) {
        //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();



        //User에 저장된 데이터와 함께 init에서 정의한 getJoinResponse 함수를 실행한 후 응답을 받음
        initMyApi.getLoginResponse(loginUser).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("kim", "retrofit Data Fetch success");
                Log.d("kim", "loginResponse: " + loginUser.toString());
                //통신 성공
                if (response.isSuccessful() && response.body().equals("1")) {

                    Log.d("kim", response.body());
                    Toast.makeText(MainActivity.this, "로그인 되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                } else if (response.isSuccessful() && response.body().equals("0")){
                    Toast.makeText(MainActivity.this, "아이디 혹은 비번이 틀렸습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "통신이 불안정 합니다", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "통신이 불안정 합니다", Toast.LENGTH_LONG).show();
            }
        });
    }


}