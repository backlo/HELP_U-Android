package com.example.help_u.Requester.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
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

    private static long lastClickTime = 0;
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
        if(preventionClick() == true) {
            Intent i = new Intent(this, RequestPopupActivity.class);
            startActivity(i);
        }
    }

    //119 버튼
    @OnClick(R.id.call_btn)
    public void call_119(){
        if(preventionClick() == true) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:119"));
            startActivity(intent);
        }
    }

    //setting 버튼
    @OnClick(R.id.setting_btn)
    public void goSettingBtn(){
        if(preventionClick() == true) {
            Intent intent = new Intent(RequestMainActivity.this, RequestSettingActivity.class);
            startActivity(intent);
        }
    }

    //더블클릭 방지 함수
    private boolean preventionClick(){
        if(SystemClock.elapsedRealtime() - lastClickTime < 1000){
            return false;
        }else{
            lastClickTime = SystemClock.elapsedRealtime();
            return true;
        }
    }


}
