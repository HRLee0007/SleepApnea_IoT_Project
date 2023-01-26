package com.example.apnea_android;

import com.example.apnea_android.info.JoinInfo;
import com.example.apnea_android.info.LoginInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface initMyApi {
    //@통신 방식("통신 API명")
    @POST("auth/joinProcAndroid") // 회원가입
    Call<String> getJoinResponse(@Body JoinInfo user);

    @POST("login") // 로그인
    Call<String> getLoginResponse(@Body LoginInfo user);

    @POST("auth/statusonAndroid") // 측정 시작
    Call<String> statusOn(@Body LoginInfo user); // 유저네임만 넘겨줘도 됨

    @POST("auth/statusoffAndroid") // 측정 종료
    Call<String> statusOff(@Body LoginInfo user); // 유저네임만 넘겨줘도 됨

}
