package com.example.help_u;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.help_u.Provider.Data.Parameter;
import com.example.help_u.Provider.Data.UserInfo;
import com.example.help_u.Provider.ProviderMainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static long lastClickTime = 0;
    private static final int LOCATION_PER = 1;
    @BindView(R.id.sign_login)
    Button sign_login;
    @BindView(R.id.sign_register)
    Button sign_register;
    @BindView(R.id.login_id)
    EditText login_id;
    @BindView(R.id.login_password)
    EditText login_password;

    private Retrofit retrofit;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        userInfo = new UserInfo();


        final Gson responseGson = new GsonBuilder()
                .registerTypeAdapter(Parameter.class, new Parameter.ParamSerializer())
                .registerTypeAdapter(Parameter.class, new Parameter.ParamDeSerializer())
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://223.194.134.216:8080")
                .addConverterFactory(GsonConverterFactory.create(responseGson))
                .build();
    }


    @OnClick(R.id.sign_login)
    public void login() {
        if (preventionClick() == true) {
            Intent i = new Intent(LoginActivity.this, ProviderMainActivity.class);
            startActivity(i);
            finish();

            /* 서버통신 테스트용 코드
                String id = login_id.getText().toString();
                String password = login_password.getText().toString();
                UserInfo userInfo = new UserInfo(id, password);

                Intent i = new Intent(LoginActivity.this, ProviderMainActivity.class);
                startActivity(i);
             */

        /*RetrofitService service = retrofit.create(RetrofitService.class);
        service.login(userInfo).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    ServerResponse serverResponse = response.body();

                    if (serverResponse != null) {
                        Log.e("로그인  response->", "" + serverResponse.getMessage() + "," + serverResponse.getResultCode()+","+serverResponse.getParam());

                        Parameter parameter = (LoginResponse)(serverResponse.getParam());

                        if (serverResponse.getResultCode() == 0 && ((LoginResponse) parameter).getUser_type().equals("provider")) {
                            Intent i = new Intent(LoginActivity.this, ProviderMainActivity.class);
                            Toast.makeText(LoginActivity.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(LoginActivity.this, RequestMainActivity.class);
                            Toast.makeText(LoginActivity.this, "" + serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(i);
                            finish();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("로그인 error ->", "" + t.toString());
            }
        });*/
            }
        }


        @OnClick(R.id.sign_register)
        public void register () {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }

        //더블클릭 방지 함수
        private boolean preventionClick () {
            if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                return false;
            } else {
                lastClickTime = SystemClock.elapsedRealtime();
                return true;
            }
        }
    }
