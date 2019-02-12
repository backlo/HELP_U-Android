package com.example.help_u.Provider.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProviderInfoFragment extends Fragment {

    @BindView(R.id.info_modify_btn)
    Button modify_btn;
    @BindView(R.id.info_ok_btn)
    Button ok_btn;

    public ProviderInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_user_info, container, false);
        ButterKnife.bind(this,view);

        return view;
    }
}
