package com.example.help_u.Provider.Util.Retrofit;


import com.example.help_u.Provider.Data.HelperRegistration;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Data.UserInfo;

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

