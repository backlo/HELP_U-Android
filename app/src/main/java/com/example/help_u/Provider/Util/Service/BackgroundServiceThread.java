package com.example.help_u.Provider.Util.Service;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.help_u.Provider.Data.LocationRequest;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackgroundServiceThread extends Thread {

    Handler handler;
    boolean isRun = true;
    //RetrofitService retrofitService;
    double lon;
    double lat;
    LocationRequest locationRequest;
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


    public void sendLocinfo(double lat, double lon){
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Log.e("service","sendlocinfo");

        locationRequest = new LocationRequest("test2", "12312313");
        retrofitService.sendLocation(locationRequest).enqueue(new retrofit2.Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    ServerResponse body = response.body();
                    Log.e("dfdsfasdf",""+body.getMessage()+","+body.getResultCode());
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
            }
        });
    }


    public void locationService() {
//        LocationManager lm = (LocationManager)context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//
//        try {
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    lon = location.getLongitude();
//                    lat = location.getLatitude();
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//
//                }
//            });
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//        Log.e("service loc >> ", lat + "," + lon);
//        if(lat !=0 && lon != 0){
//            sendLocinfo(lat, lon);
//        }
    }



}
