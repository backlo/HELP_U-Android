package com.example.help_u.Requester.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

import com.example.help_u.R;

//help 버튼 눌렀을때 뜨는 팝업 액티비티
public class RequestPopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_request_popup);

    }

    @Override
    public void onBackPressed() {
        return;
    }
}
