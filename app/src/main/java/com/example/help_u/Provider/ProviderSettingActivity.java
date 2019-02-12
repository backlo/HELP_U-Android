package com.example.help_u.Provider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.help_u.Provider.Fragment.ProviderSettingFragment;
import com.example.help_u.R;

public class ProviderSettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_setting);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.provider_setting_fragment, new ProviderSettingFragment()).commit();
    }

}
