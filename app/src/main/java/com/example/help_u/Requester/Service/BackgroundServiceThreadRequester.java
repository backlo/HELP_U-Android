package com.example.help_u.Requester.Service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.util.Log;

import com.example.help_u.Provider.Util.Retrofit.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//백그라운드에서 현재 배터리양을 확인하는 쓰레드 부분
public class BackgroundServiceThreadRequester extends Thread {

    Retrofit retrofit;
    Context context;
    boolean isRun = true;
    static boolean isSendMessage = true;
    int batteryData;
    Handler handler;

    public BackgroundServiceThreadRequester(Handler handler, Context context, int batteryData) {

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.handler = handler;
        this.context = context;
        this.batteryData = batteryData;
    }

    public void stopForever(){
        synchronized (this){
            this.isRun = false;
        }
    }

    //돌아가면서 사용자가 설정한 배터리양과 현재 배터리양이 같을경우 서버로 도움요청 보냄
    public void run(){
        while(isRun){
            try{
                Intent intentBattery = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                int level = intentBattery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intentBattery.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                int batteryPct = (int)((level / (float)scale) * 100);
                Log.e("RequesterThread >> ",batteryPct +"");

                if(batteryPct > batteryData){
                    isSendMessage = true;
                }

                if(batteryData == batteryPct) {
                    if (isSendMessage == true) {
                        Log.e("RequesterThread >> ", batteryPct + " 배터리 남음! 메시지 보내야함!");
                        handler.sendEmptyMessage(0);
                        isSendMessage = false;
                    } else {
                        Log.e("RequesterThread >> ", "메시지 이미 보냄!");
                    }


                }

                Thread.sleep(10000);
            }catch (InterruptedException e) {

            }
        }
    }
}
