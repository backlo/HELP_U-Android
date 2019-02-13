package com.example.help_u.Provider.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.help_u.Provider.Component.LogoutDialog;
import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderSettingFragment extends Fragment {

    @BindView(R.id.provider_user_info_btn)
    Button userinfo_btn;
    @BindView(R.id.provider_alarm_btn)
    Button alarm_btn;
    @BindView(R.id.provider_logout_btn)
    Button logout_btn;

    public ProviderSettingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_setting, container, false);
        ButterKnife.bind(this, view);
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
        LogoutDialog dialog = new LogoutDialog(getContext());
        dialog.show();
    }
}
