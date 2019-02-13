package com.example.help_u.Requester.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.help_u.R;
import com.example.help_u.Requester.Activity.RequestSettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestSettingFragment extends Fragment {

    @BindView(R.id.user_info_btn)
    LinearLayout userBtn;
    @BindView(R.id.call_enroll_btn)
    LinearLayout enrollBtn;
    @BindView(R.id.write_message_btn)
    LinearLayout messageBtn;
    @BindView(R.id.battery_btn)
    LinearLayout batteryBtn;
    @BindView(R.id.help_number_btn)
    LinearLayout helpNumBtn;
    @BindView(R.id.logout_btn)
    LinearLayout logoutBtn;

    public RequestSettingFragment() {   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_setting, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.user_info_btn)
    public void goUserInfo(){
        getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestUserInfoFragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.call_enroll_btn)
    public void goCallEnroll(){
        getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestEnrollFragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.write_message_btn)
    public void goWriteMessage(){
        getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestMessageFragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.battery_btn)
    public void goBattery(){
        getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestBatteryFragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.help_number_btn)
    public void goHelpNum(){
        getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestHelpFragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.logout_btn)
    public void logout(){
        AlertDialog.Builder logoutAlert = new AlertDialog.Builder(getContext());
        logoutAlert.setTitle("로그아웃");
        logoutAlert.setMessage("로그아웃 하시겠습니까?");
        logoutAlert.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //로그아웃 보내기
                Toast.makeText(getContext(),"로그아웃되었습니다.",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = logoutAlert.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface args) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        dialog.show();
    }

}
