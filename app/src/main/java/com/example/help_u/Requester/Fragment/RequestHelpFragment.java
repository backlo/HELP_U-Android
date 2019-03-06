package com.example.help_u.Requester.Fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 도움횟수 부분
public class RequestHelpFragment extends Fragment {

    @BindView(R.id.radio_help_group)
    RadioGroup helpGroup;
    @BindView(R.id.help_commit)
    Button helpCommit;
    @BindView(R.id.help_cancel)
    Button helpCancel;

    SharedPreferences sp;
    RadioButton rb;

    public RequestHelpFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_request_help, container, false);
        ButterKnife.bind(this, v);

        sp = getActivity().getSharedPreferences("Requester", Activity.MODE_PRIVATE);

        helpGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("helpcountid",checkedId);
                editor.commit();
            }
        });


        if(sp.getInt("helpcountid",0) != 0)
            helpGroup.check(sp.getInt("helpcountid",0));


        return v;
    }

    //도움 횟수 설정 버튼
    @OnClick(R.id.help_commit)
    public void helpCommit(){
        int id = helpGroup.getCheckedRadioButtonId();
        rb = (RadioButton) getActivity().findViewById(id);

        SharedPreferences.Editor editor = sp.edit();
        int count = Integer.valueOf(rb.getText().toString().replace("회",""));
        editor.putInt("helpcount",count);

        editor.commit();

        Toast.makeText(getContext(), "설정 완료!", Toast.LENGTH_SHORT).show();
    }

    //나가는 버튼
    @OnClick(R.id.help_cancel)
    public void helpCancel(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestSettingFragment()).commit();
    }

}
