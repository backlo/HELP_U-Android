package com.example.help_u.Requester.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestUserInfoFragment extends Fragment {

    @BindView(R.id.userinfo_name_edit)
    EditText nameEdit;
    @BindView(R.id.userinfo_address_edit)
    EditText addressEdit;
    @BindView(R.id.userinfo_phonenumber_id)
    EditText phoneEdit;

    public RequestUserInfoFragment() {   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_user_info, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.userinfo_commit)
    public void userinfoCommit(){
        String name = nameEdit.getText().toString();
        String address = addressEdit.getText().toString();
        String phone = phoneEdit.getText().toString();

        Log.e("userinfo >> ", name + ", " + address + ", " + phone);
    }

    @OnClick(R.id.userinfo_cancel)
    public void userinfoiCancel(){
        getActivity().onBackPressed();
    }

}
