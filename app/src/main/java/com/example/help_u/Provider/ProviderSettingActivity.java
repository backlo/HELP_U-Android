package com.example.help_u.Provider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderSettingActivity extends AppCompatActivity {

    @BindView(R.id.provider_user_info_btn)
    Button userinfo_btn;
    @BindView(R.id.provider_alarm_btn)
    Button alarm_btn;
    @BindView(R.id.provider_logout_btn)
    Button logout_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_setting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.provider_user_info_btn)
    public void userInfo(){

    }

    @OnClick(R.id.provider_alarm_btn)
    public void alarmSetting(){

    }

    @OnClick(R.id.provider_logout_btn)
    public void logOut(){

    }
}
