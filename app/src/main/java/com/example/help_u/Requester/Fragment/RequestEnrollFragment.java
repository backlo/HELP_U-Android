package com.example.help_u.Requester.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.help_u.R;
import com.example.help_u.Requester.Adapter.EnrollAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//연락처 등록 부분
public class RequestEnrollFragment extends Fragment {

    @BindView(R.id.enroll_add)
    Button add;
    @BindView(R.id.enroll_del)
    Button del;
    @BindView(R.id.enroll_commit)
    Button enrollCommit;
    @BindView(R.id.enroll_cancel)
    Button enrollCancel;
    @BindView(R.id.enroll_listview)
    ListView listView;

    EnrollAdapter enrollAdapter;

    public RequestEnrollFragment() { enrollAdapter = new EnrollAdapter(); }
    Button del;
    @BindView(R.id.enroll_commit)
    Button enrollCommit;
    @BindView(R.id.enroll_cancel)
    Button enrollCancel;
    @BindView(R.id.enroll_listview)
    ListView listView;
    EnrollAdapter enrollAdapter;

    public RequestEnrollFragment() { enrollAdapter = new EnrollAdapter(); }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_enroll, container, false);
        ButterKnife.bind(this, v);
        listView.setAdapter(enrollAdapter);
        return v;
    }

    @OnClick(R.id.enroll_add)
    public void addPhone(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.enroll_del)
    public void delPhone(){
       enrollAdapter.deleteItem();
    }

    @OnClick(R.id.enroll_commit)
    public void enrollCommit(){

    }

    @OnClick(R.id.enroll_cancel)
    public void enrollCancel(){
        getActivity().onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String[] phonepro = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Cursor cursor = getActivity().getContentResolver().query(data.getData(), phonepro, null, null, null);
        cursor.moveToFirst();

        String name = cursor.getString(0);
        String number = cursor.getString(1);

        enrollAdapter.addItem(name, number);

        cursor.close();

        super.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode,resultCode,data);
    }

}
