package com.example.help_u.Requester.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Button;

import com.example.help_u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestMainActivity extends AppCompatActivity {

    @BindView(R.id.help_btn)
    LinearLayout helpBtn;
    @BindView(R.id.call_btn)
    LinearLayout callBtn;
    @BindView(R.id.setting_btn)
    LinearLayout settingBtn;

    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_request_main);
        ButterKnife.bind(this);
    }

    //도움 요청 버튼
    @OnClick(R.id.help_btn)
    public void help_Btn(){
        Intent i = new Intent(this, RequestPopupActivity.class);
        startActivity(i);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://223.194.134.216:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    //119 버튼
    @OnClick(R.id.call_btn)
    public void call_119(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:119"));
        startActivity(intent);
    }

    //setting 버튼
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
