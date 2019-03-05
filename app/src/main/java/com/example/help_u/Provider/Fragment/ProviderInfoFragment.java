package com.example.help_u.Provider.Fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.help_u.Provider.Data.Response.UserInfoResponse;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Data.UserInfo;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.example.help_u.R;
import com.example.help_u.Requester.Fragment.RequestUserInfoFragment;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProviderInfoFragment extends Fragment {

    @BindView(R.id.info_modify_btn)
    Button modify_btn;
    @BindView(R.id.info_ok_btn)
    Button ok_btn;
    @BindView(R.id.userinfo_name)
    TextView userinfo_name;
    @BindView(R.id.userinfo_address)
    TextView userinfo_address;
    @BindView(R.id.userinfo_phonenumber)
    TextView userinfo_phonenumber;
    @BindView(R.id.userinfo_grade)
    TextView userinfo_grade;
    @BindView(R.id.userinfo_point)
    TextView userinfo_point;
    @BindView(R.id.userinfo_count)
    TextView userinfo_count;

    Retrofit retrofit;
    UserInfo userInfo_id;
    private SharedPreferences sp;

    public ProviderInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_user_info, container, false);
        ButterKnife.bind(this, view);

        sp = getActivity().getSharedPreferences("Requester", Activity.MODE_PRIVATE);

        userInfo_id = new UserInfo();

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String id = sp.getString("id", "");
        Log.e("userinfo",""+id);


        userInfo_id.setId(id);

        RetrofitService service = retrofit.create(RetrofitService.class);

        service.getInfo(userInfo_id).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                if (response.isSuccessful()) {


                    ServerResponse response1 = response.body();

                    Gson gson = new Gson();
                    String json = gson.toJson(response1.getParam());
                    UserInfoResponse loginResponse = gson.fromJson(json, UserInfoResponse.class);

                    userinfo_name.setText(loginResponse.getName());
                    userinfo_address.setText(loginResponse.getAddress());
                    userinfo_count.setText(String.valueOf(loginResponse.getCount()));
                    userinfo_grade.setText(String.valueOf(loginResponse.getGrade()));
                    userinfo_phonenumber.setText(loginResponse.getPhone());
                    userinfo_point.setText(String.valueOf(loginResponse.getPoint()));

                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

        return view;
    }

    @OnClick(R.id.info_modify_btn)
    public void modify_btn(){
        if (getFragmentManager() != null) {
            RequestUserInfoFragment.checktype = false;
            getFragmentManager().beginTransaction().replace(R.id.provider_setting_fragment, new RequestUserInfoFragment()).addToBackStack(null).commit();

        }
    }

    @OnClick(R.id.info_ok_btn)
    public void onclick_info(){

        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().replace(R.id.provider_setting_fragment, new ProviderSettingFragment()).addToBackStack(null).commit();

        }
    }
}
