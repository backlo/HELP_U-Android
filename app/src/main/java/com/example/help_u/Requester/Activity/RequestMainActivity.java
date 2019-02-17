package com.example.help_u.Requester.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestMainActivity extends AppCompatActivity {

    @BindView(R.id.help_btn)
    LinearLayout helpBtn;
    @BindView(R.id.call_btn)
    LinearLayout callBtn;
    @BindView(R.id.setting_btn)
    LinearLayout settingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_request_main);
        ButterKnife.bind(this);
    }

    //도움 요청 버튼
    @OnClick(R.id.help_btn)
    public void help_Btn(){
        Intent i = new Intent(this, RequestPopupActivity.class);
        startActivity(i);
    }

    //119 버튼
    @OnClick(R.id.call_btn)
    public void call_119(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:119"));
        startActivity(intent);
    }

    //setting 버튼
    @OnClick(R.id.setting_btn)
    public void goSettingBtn(){
        Intent intent = new Intent(RequestMainActivity.this,RequestSettingActivity.class);
        startActivity(intent);
    }


}
