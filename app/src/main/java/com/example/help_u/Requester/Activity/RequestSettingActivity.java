package com.example.help_u.Requester.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;


import com.example.help_u.R;
import com.example.help_u.Requester.Fragment.RequestSettingFragment;

//setting은 fragment로 제작
public class RequestSettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.setting_fragment, new RequestSettingFragment()).commit();
    }

}
