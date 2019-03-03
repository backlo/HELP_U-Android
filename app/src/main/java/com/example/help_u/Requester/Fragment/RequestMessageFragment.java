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
import android.widget.EditText;
import android.widget.Toast;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//메시지 부분
public class RequestMessageFragment extends Fragment {


    @BindView(R.id.message_edit)
    EditText message;
    @BindView(R.id.message_commit)
    Button messageCommit;
    @BindView(R.id.message_cancel)
    Button messageCancel;

    SharedPreferences sf;

    public RequestMessageFragment() {   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_message, container, false);
        ButterKnife.bind(this, v);

        sf = getActivity().getSharedPreferences("Requester", Activity.MODE_PRIVATE);
        message.setText(sf.getString("message",""));

        return v;
    }

    @OnClick(R.id.message_commit)
    public void messageCommit(){
        String message_data = message.getText().toString();
        Log.e("message data >> ", message_data);

        sf = getActivity().getSharedPreferences("Requester", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();

        editor.putString("message",message_data);
        editor.commit();

        Toast.makeText(getContext(), "설정 되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.message_cancel)
    public void messageCancel(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestSettingFragment()).commit();
    }

}
