package com.example.help_u.Requester.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.help_u.LoginActivity;
import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//setting초기 부분
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

    private static long lastClickTime = 0;
    SharedPreferences sp;

    public RequestSettingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_setting, container, false);
        ButterKnife.bind(this, v);
        sp = getActivity().getSharedPreferences("Requester", Context.MODE_PRIVATE);
        return v;
    }

    //사용자 설정 버튼
    @OnClick(R.id.user_info_btn)
    public void goUserInfo() {
        if (preventionClick() == true) {
            getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestUserInfoFragment()).addToBackStack(null).commit();
        }
    }

    //연락처 등록 버튼
    @OnClick(R.id.call_enroll_btn)
    public void goCallEnroll() {
        if (preventionClick() == true) {
            getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestEnrollFragment()).addToBackStack(null).commit();
        }
    }

    //메시지 작성 버튼
    @OnClick(R.id.write_message_btn)
    public void goWriteMessage() {
        if (preventionClick() == true) {
            getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestMessageFragment()).addToBackStack(null).commit();
        }
    }

    //배터리 설정 버튼
    @OnClick(R.id.battery_btn)
    public void goBattery() {
        if (preventionClick() == true) {
            getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestBatteryFragment()).addToBackStack(null).commit();
        }
    }

    //도움횟수 버튼
    @OnClick(R.id.help_number_btn)
    public void goHelpNum() {
        if (preventionClick() == true) {
            getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestHelpFragment()).addToBackStack(null).commit();
        }
    }

    //로그아웃 버튼
    @OnClick(R.id.logout_btn)
    public void logout() {
        if (preventionClick() == true) {
            //AlertDialog 알람 사용
            AlertDialog.Builder logoutAlert = new AlertDialog.Builder(getContext());
            logoutAlert.setTitle("로그아웃");
            logoutAlert.setMessage("로그아웃 하시겠습니까?");
            logoutAlert.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //로그아웃 보내기
                    Toast.makeText(getContext(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("usertype", "");
                    editor.clear();
                    editor.commit();

                    Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                    getActivity().finish();


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
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                }
            });
            dialog.show();
        }
    }

    //더블클릭 방지 함수
    private boolean preventionClick() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return false;
        } else {
            lastClickTime = SystemClock.elapsedRealtime();
            return true;
        }
    }
}