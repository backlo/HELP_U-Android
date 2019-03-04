package com.example.help_u.Requester.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.help_u.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnrollAdapter extends BaseAdapter {

    ArrayList<EnrollItem> listItem = new ArrayList<>();

    @BindView(R.id.enroll_listview_name)
    TextView enrollName;
    @BindView(R.id.enroll_listiview_number)
    TextView enrollNumber;
    @BindView(R.id.enroll_check)
    CheckBox enrollCheck;


    public EnrollAdapter() {  }


    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.enroll_custom_listview, parent, false);
            ButterKnife.bind(this, convertView);

        }

        EnrollItem enrollItem = listItem.get(position);

        enrollNumber.setText(enrollItem.getNumber());

        return convertView;
    }

    public void addItem(String number) {
        EnrollItem item = new EnrollItem();

        item.setNumber(number);

        listItem.add(item);

    }

    public void deleteItem(){
        Log.e("Enroll >> ", "삭제");
    }
}