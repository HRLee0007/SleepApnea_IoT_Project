package com.JounCamp.apnea_android.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.JounCamp.apnea_android.R;
import com.JounCamp.apnea_android.RetrofitClient;
import com.JounCamp.apnea_android.Role;
import com.JounCamp.apnea_android.info.JoinInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity  implements View.OnClickListener {
    Button join_ok_btn;
    Button join_cancel_btn;
    private RetrofitClient retrofitClient;
    private com.JounCamp.apnea_android.initMyApi initMyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join_ok_btn = (Button)findViewById(R.id.join_ok_btn);    // 회원가입 버튼을 찾고
        join_cancel_btn = (Button)findViewById(R.id.join_cancel_btn);  // 로그인 버튼을 찾고

        join_ok_btn.setOnClickListener(this);                 // 리스너를 달아줌.
        join_cancel_btn.setOnClickListener(this);             // 리스너를 달아줌.

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.join_ok_btn) {

            EditText join_id = findViewById(R.id.join_id);
            EditText join_pw = findViewById(R.id.join_pw);
            EditText join_name = findViewById(R.id.join_name);
            EditText join_email = findViewById(R.id.join_email);
            EditText join_address = findViewById(R.id.join_address);
            EditText join_myphone = findViewById(R.id.join_myphone);
            EditText join_pphone = findViewById(R.id.join_pphone);




            //로그인 정보 미입력 시
            if(join_id.getText().toString().trim().length() == 0 ||
                    join_pw.getText().toString().trim().length() == 0 ||
                    join_name.getText().toString().trim().length() == 0 ||
                    join_email.getText().toString().trim().length() == 0 ||
                    join_address.getText().toString().trim().length() == 0 ||
                    join_myphone.getText().toString().trim().length() == 0 ||
                    join_pphone.getText().toString().trim().length() == 0 ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                builder.setTitle("알림")
                        .setMessage("회원가입 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            } else {
                JoinInfo joinUser;

                joinUser = new JoinInfo(join_id.getText().toString(), join_pw.getText().toString(), join_name.getText().toString(),
                        join_email.getText().toString(), join_address.getText().toString(), join_myphone.getText().toString(), join_pphone.getText().toString(), Role.USER);

                joinResponse(joinUser);


            }
        } else if(v.getId() == R.id.join_cancel_btn) {
            Intent intent = new Intent(JoinActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void joinResponse(JoinInfo joinUser) {
        //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();

        //User에 저장된 데이터와 함께 init에서 정의한 getJoinResponse 함수를 실행한 후 응답을 받음
        initMyApi.getJoinResponse(joinUser).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("kim", "retrofit Data Fetch success");
                Log.d("kim", "joinResponse: " + joinUser.toString());
                //통신 성공
                if (response.isSuccessful()) {

                    Log.d("kim", response.body());
                    Toast.makeText(JoinActivity.this, "회원가입이 완료 되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                    //이전에 생성된 intent 전부 삭제
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    } else {
                        Toast.makeText(JoinActivity.this, "0.회원가입을 실패 했습니다.", Toast.LENGTH_LONG).show();
                    }
                }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "1.회원가입을 실패 했습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }
}