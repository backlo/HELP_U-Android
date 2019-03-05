package com.example.help_u.Requester.Service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.example.help_u.Requester.Data.LocationRequest;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyServiceRequester extends Service {

    SharedPreferences sp;
    BackgroundServiceThreadRequester thread;
    double lon;
    double lat;
    Retrofit retrofit;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Requester Service >>", "시작");

        sp = getSharedPreferences("Requester", Activity.MODE_PRIVATE);
        int batteryAmount = sp.getInt("batteryAmount", 0);
        LocationRequesterServiceHandler handler = new LocationRequesterServiceHandler();

        thread = new BackgroundServiceThreadRequester(handler, getApplicationContext(), batteryAmount);
        thread.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        thread.stopForever();
        thread = null;

        Log.e("Requester Service >>", "종료");
        super.onDestroy();
    }

    class LocationRequesterServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.e("requester handler >> ", "handler 시작");
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
            if (lat == 0 && lon == 0) {
                Log.e("requester handler >> ", "sendBatteryMessage시작");
                sendBatteryMessage(lat, lon);
            }
        }

        public void sendBatteryMessage(double lat, double lon) {

            String id = sp.getString("id", "");
            String name = sp.getString("name","");
            String message = name + "님의 배터리가 " + sp.getInt("batteryAmount",0) + "% 남았습니다.";

            int count = sp.getInt("helpcount", 0);

            if ("".equals(message)) {
                message = "도와주세요!";
            }
            if (count == 0) {
                count = 5;
            }

            Log.e("requester handler >> ", "id: " + id + ", message: " + message + ", count: " + count + ", (" + lat + ", " + lon + ")");

            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            LocationRequest locationRequest = new LocationRequest("" + lat + "," + lon, id, message, count);
            retrofitService.sendLocation(locationRequest).enqueue(new retrofit2.Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        ServerResponse body = response.body();
                        Log.e("service", "body" + body.getResultCode());
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e("service", "send loc error->" + t.toString());
                    Toast.makeText(getApplicationContext(), "서버 연결 실패", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}