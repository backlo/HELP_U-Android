package com.example.help_u.Provider.Fragment;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.help_u.Provider.Data.SendAlarmSet;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProviderAlarmFragment extends Fragment {

    @BindView(R.id.sound_radio)
    RadioButton sound_radio;
    @BindView(R.id.vibrate_radio)
    RadioButton vibrate_radio;
    @BindView(R.id.alarm_switch)
    Switch alarm_switch;

    AudioManager aManager;
    Retrofit retrofit;
    SendAlarmSet sendAlarmSet;
    SharedPreferences sp;
    String id;
    SharedPreferences.Editor editor;

    public ProviderAlarmFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_alarm, container, false);
        ButterKnife.bind(this,view);

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sp = getActivity().getSharedPreferences("Requester", Activity.MODE_PRIVATE);
        id = sp.getString("id", "");
        editor = sp.edit();

        if (sp != null) {
            if(sp.getString("alarm","").equals("on")){
                alarm_switch.setChecked(true);
            }else{
                alarm_switch.setChecked(false);
            }
        }



        aManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if(aManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
            sound_radio.setChecked(true);
        }else if(aManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
            vibrate_radio.setChecked(true);
        }else{
            vibrate_radio.setChecked(false);
            vibrate_radio.setChecked(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }

        return view;
    }

    @OnCheckedChanged(R.id.alarm_switch)
    public void alarmSwitch(){
        //알람 스위치 on,off 이벤트
        if(alarm_switch.isChecked()){
            editor = sp.edit();
            editor.putString("alarm","on");
            editor.commit();

            SendAlarmSet sendAlarmSet1 = new SendAlarmSet(id, true);

            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            retrofitService.setAlarm(sendAlarmSet1).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    Log.e("alarm On request","success");
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e("alarm On request","fail"+t.toString());
                }
            });
        }else{

            editor = sp.edit();
            editor.putString("alarm","off");
            editor.commit();
            sendAlarmSet = new SendAlarmSet(id, false);
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            retrofitService.setAlarm(sendAlarmSet).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    Log.e("alarm oFF request","success");
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e("alarm oFF request","fail"+t.toString());
                }
            });
        }

    }

    @OnCheckedChanged(R.id.sound_radio)
    public void soundRadioChecked(){
        if(sound_radio.isChecked() || aManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE || aManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
            aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }

    @OnCheckedChanged(R.id.vibrate_radio)
    public void vibrateRadioChecked(){
        if(vibrate_radio.isChecked() || aManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL || aManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
            aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
    }
}
