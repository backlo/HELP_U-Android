package com.example.help_u.Provider.Fragment;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class ProviderAlarmFragment extends Fragment {

    @BindView(R.id.sound_radio)
    RadioButton sound_radio;
    @BindView(R.id.vibrate_radio)
    RadioButton vibrate_radio;
    @BindView(R.id.alarm_switch)
    Switch alarm_switch;

    AudioManager aManager;

    public ProviderAlarmFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_alarm, container, false);
        ButterKnife.bind(this,view);
        aManager = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);


        return view;
    }

    @OnCheckedChanged(R.id.alarm_switch)
    public void alarmSwitch(){
        Toast.makeText(getActivity(), "알람 스위치 테스트", Toast.LENGTH_SHORT).show();
    }

    @OnCheckedChanged(R.id.sound_radio)
    public void soundRadioChecked(){
        if(sound_radio.isChecked() && aManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
            aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }

        Toast.makeText(getActivity(), "소리 라디오", Toast.LENGTH_SHORT).show();
    }

    @OnCheckedChanged(R.id.vibrate_radio)
    public void vibrateRadioChecked(){
        if(vibrate_radio.isChecked() && aManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
            aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }

        Toast.makeText(getActivity(), "진동라디오", Toast.LENGTH_SHORT).show();
    }
}
