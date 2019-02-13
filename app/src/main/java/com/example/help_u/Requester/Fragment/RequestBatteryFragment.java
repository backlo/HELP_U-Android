package com.example.help_u.Requester.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RequestBatteryFragment extends Fragment {

    @BindView(R.id.radio_battery_group)
    RadioGroup batteryGroup;
    @BindView(R.id.battery_commit)
    Button batteryCommit;
    @BindView(R.id.battery_cancel)
    Button batteryCancel;

    public RequestBatteryFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_battery, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.battery_commit)
    public void batteryCommit(){
        int id = batteryGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) getActivity().findViewById(id);
        Log.e("battery data >> ",rb.getText() + "");

    }

    @OnClick(R.id.battery_cancel)
    public void batteryCancel(){
        getActivity().onBackPressed();
    }

}
