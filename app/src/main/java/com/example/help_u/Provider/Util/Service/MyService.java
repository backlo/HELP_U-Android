package com.example.help_u.Provider.Util.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.help_u.Provider.Data.LocationRequest_provider;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyService extends Service {

    BackgroundServiceThread thread;
    NetworkService thread1;
    double lon;
    double lat;
    LocationRequest_provider locationRequestProvider;
    Retrofit retrofit;
    RetrofitService retrofitService;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //서비스 실행시 한번 실행
    @Override
    public void onCreate() {
        LocationManager lm;

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        super.onCreate();
    }

    //백그라운드에서 실행되는 동작 구현(네트워크)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service!@!@!@", "background ok");
        locationServiceHandler handler = new locationServiceHandler();

        thread = new BackgroundServiceThread(handler, getApplicationContext());

        thread.start();
        return START_STICKY;
    }

    //서비스가 종료될 때 실행되는 메소드 (앱이 다시 살때)
    @Override
    public void onDestroy() {
        thread.stopForever();
        thread = null;
        super.onDestroy();
    }


    class locationServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.e("locationservicehandler", "handler");
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

            try {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        lon = location.getLongitude();
                        lat = location.getLatitude();
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            Log.e("service loc >> ", lat + "," + lon);
            if (lat != 0 && lon != 0) {
                sendLocinfo(lat, lon);
            }
            //sendLocinfo(lat, lon);
        }

        public void sendLocinfo(double lat, double lon){
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            Log.e("service","sendlocinfo");

            locationRequestProvider = new LocationRequest_provider("test2", "12312313");
            retrofitService.sendLocation_Provider(locationRequestProvider).enqueue(new retrofit2.Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if(response.isSuccessful()){
                        ServerResponse body = response.body();
                        Log.e("dfdsfasdf",""+body.getMessage()+","+body.getResultCode());
                    }
                }
                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e("service","send loc error->"+t.toString());
                }
            });
        }
    }
}
