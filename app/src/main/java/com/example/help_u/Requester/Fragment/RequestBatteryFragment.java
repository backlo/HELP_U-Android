package com.example.help_u.Requester.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.help_u.R;
import com.example.help_u.Requester.Service.MyServiceRequester;

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

    SharedPreferences sp;

    RadioButton rb;

    public RequestBatteryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_battery, container, false);
        ButterKnife.bind(this, v);

        sp = getActivity().getSharedPreferences("Requester", Activity.MODE_PRIVATE);

        batteryGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("batteryid",checkedId);
                editor.commit();
            }
        });


        if(sp.getInt("batteryid",0) != 0)
            batteryGroup.check(sp.getInt("batteryid",0));


        return v;
    }

    //베터리 량 확인부분
    @OnClick(R.id.battery_commit)
    public void batteryCommit() {
        int id = batteryGroup.getCheckedRadioButtonId();
        rb = (RadioButton) getActivity().findViewById(id);
        String rbData = rb.getText().toString().replace("%","");

        SharedPreferences.Editor editor = sp.edit();
        int batteryAmount = Integer.valueOf(rbData);
        editor.putInt("batteryAmount",batteryAmount);

        editor.commit();

        Intent intent = new Intent(getActivity().getApplicationContext(), MyServiceRequester.class);
        getActivity().stopService(intent);
        getActivity().startService(intent);

        Toast.makeText(getActivity().getApplicationContext(),"설정완료되었습니다",Toast.LENGTH_SHORT).show();

    }

    //취소 부분
    @OnClick(R.id.battery_cancel)
    public void batteryCancel() {
        getActivity().onBackPressed();
    }

}
