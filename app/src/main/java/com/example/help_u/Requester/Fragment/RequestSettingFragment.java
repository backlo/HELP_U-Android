package com.example.help_u.Requester.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestSettingFragment extends Fragment {

    @BindView(R.id.user_info_btn)
    Button userBtn;
    @BindView(R.id.call_enroll_btn)
    Button enrollBtn;
    @BindView(R.id.write_message_btn)
    Button messageBtn;
    @BindView(R.id.battery_btn)
    Button batteryBtn;
    @BindView(R.id.help_number_btn)
    Button helpNumBtn;
    @BindView(R.id.logout_btn)
    Button logoutBtn;

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
        Toast.makeText(getActivity().getApplicationContext(), "로그아웃 버튼!", Toast.LENGTH_SHORT).show();
    }

}
