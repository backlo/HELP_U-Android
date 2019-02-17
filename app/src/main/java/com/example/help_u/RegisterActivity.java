package com.example.help_u;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.help_u.Provider.Data.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.edit_id)
    EditText edit_id;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.edit_phonenum)
    EditText edit_phonenum;
    @BindView(R.id.edit_psw)
    EditText edit_psw;
    @BindView(R.id.edit_psw_checker)
    TextView edit_psw_checker;
    @BindView(R.id.edit_psw_second)
    EditText edit_psw_second;
    @BindView(R.id.edit_residentnum)
    EditText edit_residentnum;
    @BindView(R.id.signup_btn)
    Button signup_btn;
    @BindView(R.id.help_radio)
    RadioButton help_radio;
    @BindView(R.id.provider_radio)
    RadioButton provider_radio;
    @BindView(R.id.overlap_check_btn)
    Button overlap_check_btn;
    @BindView(R.id.overrlap_result_txt)
    TextView overrlap_result_txt;
    @BindView(R.id.radio_girl)
    RadioButton radio_girl;
    @BindView(R.id.radio_man)
    RadioButton radio_man;



    Retrofit retrofit;
    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        userInfo = new UserInfo();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://223.194.134.216:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    //아이디 중복체크 버튼
    @OnClick(R.id.overlap_check_btn)
    public void overLapCheck(){
//        if(edit_id.getText() == null){
//            overrlap_result_txt.setText("아이디를 먼저 입력해주세요");
//        }else if(){
//            //중복 되는 경우 있을때
//        }else{
//            //중복체크 통과
//        }

        /*서버 테스트용
        String check_id = edit_id.getText().toString();
        if(check_id != null){
            UserInfo duplicateId = new UserInfo(check_id);
            RetrofitService service = retrofit.create(RetrofitService.class);
            service.idCheck(duplicateId).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if(response.isSuccessful()){
                        ServerResponse result = response.body();
                        Log.e("중복체크 response->",""+result.getResultCode()+","+result.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e("error->",""+t.toString());

                }
            });
        }else{
            overrlap_result_txt.setText("아이디를 입력해주세요.");
        }

         */


    }
    //회원가입 완료 버튼
    @OnClick(R.id.signup_btn)
    public void signup_btn(){
        //다른속성 Null 체크해야됨
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //이미 아이디가 있을경우 바로 로그인화면으로
    @OnClick(R.id.textViewSignin)
    public void goToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);

        /*서버테스트용
                String id = edit_id.getText().toString();
        String psw = edit_psw.getText().toString();
        String name = edit_name.getText().toString();
        String phone = edit_phonenum.getText().toString();
        String personal_num = edit_residentnum.getText().toString();
        int gender = -1;
        if(radio_girl.isChecked()){
            gender = 1;
        }
        if(radio_man.isChecked()){
            gender = 0;
        }
        String user_type = "";
        if(help_radio.isChecked()){
            user_type = "requester";
        }else{
            user_type = "provider";
        }

        userInfo.setId(id);
        userInfo.setPassword(psw);
        userInfo.setName(name);
        userInfo.setPhone(phone);
        userInfo.setPersonal_no(personal_num);
        userInfo.setGender(gender);
        userInfo.setUser_type(user_type);
        userInfo.setToken("testtoken");

        RetrofitService service = retrofit.create(RetrofitService.class);
        service.sendUserInfo(userInfo).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    ServerResponse result = response.body();
                    Log.e("회원가입 response->",""+result.getMessage()+","+result.getResultCode());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("error->",""+t.toString());
            }
        });*/


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
