package com.example.help_u.Requester.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.help_u.R;
import com.example.help_u.Requester.Fragment.RequestSettingFragment;

public class RequestSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_setting);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.setting_fragment, new RequestSettingFragment()).commit();

    }

}
