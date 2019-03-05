package com.example.help_u.Provider.Util.Retrofit;


import com.example.help_u.Provider.Data.LocationRequest_provider;
import com.example.help_u.Provider.Data.NotificationRequest;
import com.example.help_u.Requester.Data.AddNumber;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Data.UserInfo;
import com.example.help_u.Requester.Data.GetUserInfo;
import com.example.help_u.Requester.Data.HelpRegistration;
import com.example.help_u.Requester.Data.LocationRequest;
import com.example.help_u.Requester.Data.Modify_User;
import com.example.help_u.Requester.Data.RemoveNumber;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService  {

    String URL = "http://ec2-18-224-153-16.us-east-2.compute.amazonaws.com:8080/";

    @POST("/helpu/user/registration")
    Call<ServerResponse> sendUserInfo(@Body UserInfo userInfo);

    @POST("/helpu/user/id/check")
    Call<ServerResponse> idCheck(@Body UserInfo userInfo);

    @POST("/helpu/user/info")
    Call<ServerResponse> getInfo(@Body UserInfo userInfo);

    @POST("/helpu/user/login")
    Call<ServerResponse> login(@Body UserInfo userInfo);

    @POST("/helpu/provider/phones")
    Call<ServerResponse> helperRegistration(@Body HelpRegistration helpRegistration);

    @POST("/helpu/help/request")
    Call<ServerResponse> sendLocation(@Body LocationRequest locationRequest);

    @POST("/helpu/provider/location")
    Call<ServerResponse> sendLocation_Provider(@Body LocationRequest_provider locationRequestProvider);

    @POST("/helpu/help/accept")//provider requester 둘다 아이디
    Call<ServerResponse> atReadNotification(@Body NotificationRequest notificationRequest);

    @POST("/helpu/help/registration")
    Call<ServerResponse> addNumber(@Body AddNumber addNumber);

    @POST("/helpu/help/remove")
    Call<ServerResponse> removeNumber(@Body RemoveNumber removeNumber);

    @POST("/helpu/user/info")
    Call<ServerResponse> getUserInfo(@Body GetUserInfo getUserInfo);

    @POST("/helpu/user/update")
    Call<ServerResponse> modify_User(@Body Modify_User modify_user);
}

