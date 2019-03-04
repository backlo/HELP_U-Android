package com.example.help_u.Requester.Fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.help_u.Provider.Data.UserInfo;
import com.example.help_u.Requester.Data.Modify_User;
import com.example.help_u.Requester.Data.Response.ProviderPhonesResponse;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.example.help_u.R;
import com.example.help_u.Requester.Data.GetUserInfo;
import com.example.help_u.Requester.Data.Response.RequesterInfo;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//사용자 정보 부분
public class RequestUserInfoFragment extends Fragment {

    @BindView(R.id.userinfo_name_edit)
    EditText nameEdit;
    @BindView(R.id.userinfo_address_edit)
    EditText addressEdit;
    @BindView(R.id.userinfo_phonenumber_id)
    EditText phoneEdit;

    Retrofit retrofit;
    private SharedPreferences sp;

    public RequestUserInfoFragment() {   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_user_info, container, false);
        ButterKnife.bind(this, v);
        sp = getActivity().getSharedPreferences("Requester", Activity.MODE_PRIVATE);
        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getUserInfo();

        return v;
    }

    @OnClick(R.id.userinfo_commit)
    public void userinfoCommit(){
        String name = nameEdit.getText().toString();
        String address = addressEdit.getText().toString();
        String phone = phoneEdit.getText().toString();

        Log.e("userinfo 수정 -> ", name + ", " + address + ", " + phone);

        final RetrofitService service = retrofit.create(RetrofitService.class);

        String id = sp.getString("id","");
        final Modify_User modify_user = new Modify_User(id, name, phone, address);
        service.modify_User(modify_user).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    ServerResponse serverResponse = response.body();
                    Log.e("userinfo 수정 -> ", "" + serverResponse.getMessage() + "," + serverResponse.getResultCode());
                    Toast.makeText(getContext(),"설정 하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("userinfo 수정 -> ", "실패");
            }
        });
    }

    @OnClick(R.id.userinfo_cancel)
    public void userinfoiCancel(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestSettingFragment()).commit();
    }



    private void getUserInfo(){

        final RetrofitService service = retrofit.create(RetrofitService.class);

        String id = sp.getString("id", "");
        Log.e("UserInfo >> ", id);

        final GetUserInfo getUserInfo = new GetUserInfo(id);
        service.getUserInfo(getUserInfo).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    ServerResponse serverResponse = response.body();
                    Log.e("UserInfo값 response->", "" + serverResponse.getMessage() + "," + serverResponse.getResultCode());

                    Gson gson = new Gson();
                    String json = gson.toJson(serverResponse.getParam()); // May not serialize foo.value correctly
                    RequesterInfo requesterInfo = gson.fromJson(json, RequesterInfo.class); // Fails to deserialize foo.value as Bar

                    Log.e("UserInfo >> ", "" + requesterInfo.getName()+", "+requesterInfo.getPhone()+", "+requesterInfo.getAddress());

                    nameEdit.setText(requesterInfo.getName());
                    phoneEdit.setText(requesterInfo.getPhone());
                    addressEdit.setText(requesterInfo.getAddress());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("UserInfo값 error->", "" + t.toString());
                Toast.makeText(getContext(), "서버에 연결할수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
