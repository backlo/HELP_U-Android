package com.example.help_u.Provider.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.help_u.LoginActivity;
import com.example.help_u.Provider.Util.Service.MyService;
import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderSettingFragment extends Fragment {

    @BindView(R.id.provider_user_info_btn)
    LinearLayout userinfo_btn;
    @BindView(R.id.provider_alarm_btn)
    LinearLayout alarm_btn;
    @BindView(R.id.provider_logout_btn)
    LinearLayout logout_btn;

    private static long lastClickTime = 0;
    SharedPreferences sp;

    public ProviderSettingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_setting, container, false);
        ButterKnife.bind(this, view);
        sp  = getActivity().getSharedPreferences("Requester", Context.MODE_PRIVATE);
        return view;
    }

    @OnClick(R.id.provider_user_info_btn)
    public void userInfo(){
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().replace(R.id.provider_setting_fragment, new ProviderInfoFragment()).addToBackStack(null).commit();
        }
    }

    @OnClick(R.id.provider_alarm_btn)
    public void alarmSetting(){
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().replace(R.id.provider_setting_fragment, new ProviderAlarmFragment()).addToBackStack(null).commit();
        }
    }

    @OnClick(R.id.provider_logout_btn)
    public void logOut(){
        /*커스텀해서 사용하려면 이걸로
         LogoutDialog dialog = new LogoutDialog(getContext());
        dialog.show();*/

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
                    editor.putString("usertype","");
                    editor.clear();
                    editor.commit();

                    Intent intent = new Intent(getActivity().getApplicationContext(), MyService.class);
                    getActivity().stopService(intent);

                    Intent logoutIntent = new Intent(getActivity(), LoginActivity.class);
                    logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logoutIntent);

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
    private boolean preventionClick(){
        if(SystemClock.elapsedRealtime() - lastClickTime < 1000){
            return false;
        }else{
            lastClickTime = SystemClock.elapsedRealtime();
            return true;
        }
    }
}
