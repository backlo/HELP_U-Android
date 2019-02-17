package com.example.help_u.Util;

import com.example.help_u.model.HelperRegistration;
import com.example.help_u.model.ServerResponse;
import com.example.help_u.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService  {

    @POST("/helpu/user/registration")
    Call<ServerResponse> sendUserInfo(@Body UserInfo userInfo);

    @POST("/helpu/user/id/check")
    Call<ServerResponse> idCheck(@Body UserInfo userInfo);

    @POST("/helpu/user/login")
    Call<ServerResponse> login(@Body UserInfo userInfo);

    @POST("/helpu/help/registration")
    Call<ServerResponse> helperRegistration(@Body HelperRegistration helperRegistration);

}
