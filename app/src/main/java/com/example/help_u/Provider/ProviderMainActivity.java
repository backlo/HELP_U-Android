package com.example.help_u.Provider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderMainActivity extends AppCompatActivity {

    @BindView(R.id.main_provider_setting_btn)
    Button setting_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.main_provider_setting_btn)
    public void goSetting(){
        Intent intent = new Intent(ProviderMainActivity.this, ProviderSettingActivity.class );
        startActivity(intent);
    }

}
