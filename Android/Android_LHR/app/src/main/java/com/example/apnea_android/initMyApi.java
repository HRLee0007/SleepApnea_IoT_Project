package com.example.apnea_android;

import com.example.apnea_android.info.JoinInfo;
import com.example.apnea_android.info.LoginInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface initMyApi {
    //@통신 방식("통신 API명")
    @POST("auth/joinProcAndroid")
    Call<String> getJoinResponse(@Body JoinInfo user);

    @POST("login")
    Call<String> getLoginResponse(@Body LoginInfo user);

    @POST("auth/statuson")
    Call<String> statusOn(@Body JoinInfo user);

}
