package com.example.help_u.Provider.Util.Service;

import android.os.Handler;

public class NetworkService extends Thread {

    Handler handler;
    boolean isRun = true;

    public NetworkService(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(0);
        try{
            Thread.sleep(3000);
        }catch (Exception e){

        }
        super.run();
    }

    public void stopForever(){
        synchronized (this){
            this.isRun = false;
        }
    }
}
