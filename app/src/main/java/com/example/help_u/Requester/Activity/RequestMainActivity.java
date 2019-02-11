package com.example.help_u.Requester.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestMainActivity extends AppCompatActivity {

    @BindView(R.id.help_btn)
    Button helpBtn;
    @BindView(R.id.call_btn)
    Button callBtn;
    @BindView(R.id.setting_btn)
    Button settingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.call_btn)
    public void call_119(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:119"));
        startActivity(intent);
    }

    @OnClick(R.id.setting_btn)
    public void goSettingBtn(){
        Intent intent = new Intent(RequestMainActivity.this,RequestSettingActivity.class);
        startActivity(intent);
    }
}
