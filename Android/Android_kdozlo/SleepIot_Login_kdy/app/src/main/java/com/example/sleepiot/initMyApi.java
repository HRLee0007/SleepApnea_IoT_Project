package com.example.sleepiot;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface initMyApi {
    //@통신 방식("통신 API명")
    @POST("/auth/joinProcAndroid")
    Call<String> getJoinResponse(@Body JoinInfo user);

    @POST("login")
    Call<String> getLoginResponse(@Body LoginInfo user);
}
