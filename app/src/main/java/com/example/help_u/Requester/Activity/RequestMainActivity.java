package com.example.help_u.Requester.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestMainActivity extends AppCompatActivity {

    @BindView(R.id.help_btn)
    Button helpBtn;
    @BindView(R.id.call_btn)
    Button callBtn;
    @BindView(R.id.setting_btn)
    Button settingBtn;

    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_main);
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://223.194.134.216:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @OnClick(R.id.call_btn)
    public void call_119(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:119"));
        startActivity(intent);
    }

    @OnClick(R.id.setting_btn)
    public void goSettingBtn(){
        Intent intent = new Intent(RequestMainActivity.this,RequestSettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.help_btn)
    public void sendHelp(){
//        RetrofitService service = retrofit.create(RetrofitService.class);
//        RequestHelp requestHelp = new RequestHelp("test1","01000001234");
//        service.helpRequest(requestHelp).enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                if(response.isSuccessful()){
//                    ServerResponse serverResponse = response.body();
//                    Log.e("도움요청 response->",""+serverResponse.getResultCode()+","+serverResponse.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                    Log.e("도움요청 error->",""+t.toString());
//            }
//        });
    }
}
