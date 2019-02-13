package com.example.help_u.Provider.Component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.help_u.R;

public class LogoutDialog extends Dialog implements View.OnClickListener {
    private static final int LAYOUT = R.layout.logout_dialog;

    private Context context;

    private TextView ok_logout;
    private TextView cancel_logout;

    public LogoutDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);


        ok_logout = (TextView) findViewById(R.id.custom_logout_ok);
        cancel_logout = (TextView) findViewById(R.id.custom_logout_cancel);

        ok_logout.setOnClickListener(this);
        cancel_logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //취소
            case R.id.custom_logout_cancel:
                cancel();
                break;

            //로그아웃처리
            case R.id.custom_logout_ok:
                cancel();
                break;
        }
    }
}
