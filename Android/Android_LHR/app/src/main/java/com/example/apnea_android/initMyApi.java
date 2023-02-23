package com.example.apnea_android;

import com.example.apnea_android.info.CountInfo;
import com.example.apnea_android.info.JoinInfo;
import com.example.apnea_android.info.LoginInfo;
import com.example.apnea_android.info.MeasureRequestInfo;
import com.example.apnea_android.info.ResponseInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface initMyApi {
    //@통신 방식("통신 API명")
    @POST("auth/joinProcAndroid") // 회원가입
    Call<String> getJoinResponse(@Body JoinInfo user);

    @POST("auth/loginProcAndroid") // 로그인
    Call<ResponseInfo<JoinInfo>> getLoginResponse(@Body LoginInfo user);

    @POST("auth/statusAndroid") // 측정 시작
    Call<Integer> getStatusResponse(@Body MeasureRequestInfo user); // 유저네임만 넘겨줘도 됨

    @GET("/api/v1/count") //무호흡 측정 현황
    Call<List<CountInfo>> getUserCount(@Query("username") String username);

}
