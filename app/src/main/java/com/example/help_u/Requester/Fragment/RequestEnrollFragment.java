package com.example.help_u.Requester.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestEnrollFragment extends Fragment {

    @BindView(R.id.enroll_add)
    Button add;
    @BindView(R.id.enroll_del)
    Button del;
    @BindView(R.id.enroll_commit)
    Button enrollCommit;
    @BindView(R.id.enroll_cancel)
    Button enrollCancel;

    public RequestEnrollFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_enroll, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.enroll_add)
    public void addPhone(){

    }

    @OnClick(R.id.enroll_del)
    public void delPhone(){

    }

    @OnClick(R.id.enroll_commit)
    public void enrollCommit(){

    }

    @OnClick(R.id.enroll_cancel)
    public void enrollCancel(){
        getActivity().onBackPressed();
    }

}
