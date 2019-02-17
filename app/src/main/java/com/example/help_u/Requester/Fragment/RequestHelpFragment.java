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

// 도움횟수 부분
public class RequestHelpFragment extends Fragment {

    @BindView(R.id.radio_help_group)
    RadioGroup helpGroup;
    @BindView(R.id.help_commit)
    Button helpCommit;
    @BindView(R.id.help_cancel)
    Button helpCancel;

    public RequestHelpFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_request_help, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.help_commit)
    public void helpCommit(){
        int id = helpGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) getActivity().findViewById(id);
        Log.e("help_num data >> ",rb.getText() + "");

    }

    @OnClick(R.id.help_cancel)
    public void helpCancel(){
        getActivity().onBackPressed();
    }

}
