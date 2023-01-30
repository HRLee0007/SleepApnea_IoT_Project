package com.example.apnea_android;

import com.example.apnea_android.info.JoinInfo;
import com.example.apnea_android.info.LoginInfo;
import com.example.apnea_android.info.MeasureRequestInfo;
import com.example.apnea_android.info.ResponseInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface initMyApi {
    //@통신 방식("통신 API명")
    @POST("auth/joinProcAndroid") // 회원가입
    Call<String> getJoinResponse(@Body JoinInfo user);

    @POST("auth/loginProcAndroid") // 로그인
    Call<ResponseInfo<JoinInfo>> getLoginResponse(@Body LoginInfo user);

    @POST("auth/statusAndroid") // 측정 시작
    Call<Integer> getStatusResponse(@Body MeasureRequestInfo user); // 유저네임만 넘겨줘도 됨

}
