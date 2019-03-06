package com.example.help_u;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Data.UserInfo;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    @BindView(R.id.edit_address)
    EditText edit_address;

    @BindView(R.id.regist_linear)
    LinearLayout regist_linear;

    InputMethodManager inputMethodManager;

    Retrofit retrofit;
    UserInfo userInfo;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Log.e("at login token",""+ FirebaseInstanceId.getInstance().getToken());
        userInfo = new UserInfo();
        token =  FirebaseInstanceId.getInstance().getToken();
        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        inputMethodManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
    }

    @OnClick(R.id.regist_linear)
    public void clickView(){
        inputMethodManager.hideSoftInputFromWindow(edit_id.getWindowToken(), 0);
    }

    //아이디 중복체크 버튼
    @OnClick(R.id.overlap_check_btn)
    public void overLapCheck(){

        String check_id = edit_id.getText().toString();
        if(check_id != null){
            UserInfo duplicateId = new UserInfo(check_id);
            RetrofitService service = retrofit.create(RetrofitService.class);
            service.idCheck(duplicateId).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if(response.isSuccessful()){
                        ServerResponse result = response.body();
                        if(result.getResultCode() == 0){
                            overrlap_result_txt.setText("사용하실수있는아이디입니다.");
                        }else{
                            overrlap_result_txt.setText("이미사용중인아이디입니다.");
                        }
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




    }
    //회원가입 완료 버튼
    @OnClick(R.id.signup_btn)
    public void signup_btn(){

        //다른속성 Null 체크해야됨
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);

        String id = edit_id.getText().toString();
        String password = edit_psw.getText().toString();
        String name = edit_name.getText().toString();
        String phone = edit_phonenum.getText().toString();
        String personal_num = edit_residentnum.getText().toString();
        String address = edit_address.getText().toString();
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
        userInfo.setPassword(password);
        userInfo.setName(name);
        userInfo.setPhone(phone);
        userInfo.setPersonal_no(personal_num);
        userInfo.setGender(gender);
        userInfo.setUser_type(user_type);
        userInfo.setAddress(address);
        userInfo.setToken(token);

        //Requester 이름저장
        SharedPreferences sp = getSharedPreferences("Requester", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("name",id);
        editor.commit();

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
        });

    }

    //이미 아이디가 있을경우 바로 로그인화면으로
    @OnClick(R.id.textViewSignin)
    public void goToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
