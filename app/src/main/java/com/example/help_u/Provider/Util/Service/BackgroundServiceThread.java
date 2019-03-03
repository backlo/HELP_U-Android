package com.example.help_u.Provider.Util.Service;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.help_u.Provider.Data.LocationRequest_provider;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackgroundServiceThread extends Thread {

    Handler handler;
    boolean isRun = true;
    //RetrofitService retrofitService;
    double lon;
    double lat;
    LocationRequest_provider locationRequestProvider;
    Retrofit retrofit;
    Context context;

    public BackgroundServiceThread(Handler handler, Context context){

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.handler = handler;
        this.context = context;
    }

    public void stopForever(){
        synchronized (this){
            this.isRun = false;
        }
    }

    @Override
    public void run() {
        super.run();
        while(isRun){
            Log.e("service handler run","backgroundservicethread");
            handler.sendEmptyMessage(0);
            try{
                Thread.sleep(3000);
            }catch (Exception e){

            }
        }
    }


}
