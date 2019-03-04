package com.example.help_u.Requester.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.help_u.R;
import com.example.help_u.Requester.Activity.RequestSettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//베터리 설정 부분
//implements RequestSettingActivity.OnBackPressListener
public class RequestBatteryFragment extends Fragment {

    @BindView(R.id.radio_battery_group)
    RadioGroup batteryGroup;
    @BindView(R.id.battery_commit)
    Button batteryCommit;
    @BindView(R.id.battery_cancel)
    Button batteryCancel;

    public RequestBatteryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_battery, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.battery_commit)
    public void batteryCommit() {
        int id = batteryGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) getActivity().findViewById(id);
        if(!rb.getText().equals(""))
            Log.e("battery data >> ", rb.getText() + "");

    }

    @OnClick(R.id.battery_cancel)
    public void batteryCancel() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestSettingFragment()).commit();
    }


//    @Override
//    public void onBack() {
//        Log.e("!!!","onBack()");
//        RequestSettingActivity requestSettingActivity = (RequestSettingActivity)getActivity();
//        requestSettingActivity.setOnBackPressedListener(null);
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestSettingFragment()).commit();
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.e("!!!", "onAttach()");
//        ((RequestSettingActivity)context).setOnBackPressedListener(this);
//    }
}
