package com.example.help_u.Requester.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;


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

//    public interface OnBackPressListener {
//        public void onBack();
//    }
//
//    private OnBackPressListener mBackpressListener;
//
//    public void setOnBackPressedListener(OnBackPressListener listener) {
//        mBackpressListener = listener;
//    }

    @Override
    public void onBackPressed() {
//        if (mBackpressListener != null) {
//            mBackpressListener.onBack();
//            Log.e("!!!", "Listener is not null");
//        } else {
//            Log.e("!!!", "Listener is null");
//            super.onBackPressed();
//            Log.e("!!!", "onBackPressed : finish, killProcess");
            Intent intent = new Intent(this, RequestMainActivity.class);
            startActivity(intent);
            finish();
//            android.os.Process.killProcess(android.os.Process.myPid());

//        }
    }


}
