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

public class RequestMessageFragment extends Fragment {


    @BindView(R.id.message_edit)
    EditText message;
    @BindView(R.id.message_commit)
    Button messageCommit;
    @BindView(R.id.message_cancel)
    Button messageCancel;

    public RequestMessageFragment() {   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_message, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.message_commit)
    public void messageCommit(){
        String message_data = message.getText().toString();
        Log.e("message data >> ", message_data);

    }

    @OnClick(R.id.message_cancel)
    public void messageCancel(){
        getActivity().onBackPressed();
    }

}
