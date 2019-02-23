package com.example.help_u;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.help_u.Provider.Data.Parameter;
import com.example.help_u.Provider.Data.Response.LoginResponse;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Data.UserInfo;
import com.example.help_u.Provider.ProviderMainActivity;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.example.help_u.Requester.Activity.RequestMainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    @BindView(R.id.imageview)
    ImageView imageView;

    private Retrofit retrofit;
    private UserInfo userInfo;

    private final int REQUEST_WIDTH = 512;
    private final int REQUEST_HEIGHT = 512;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        modifyBitmap(R.drawable.main);

        userInfo = new UserInfo();


        final Gson responseGson = new GsonBuilder()
                .registerTypeAdapter(Parameter.class, new Parameter.ParamSerializer())
                .registerTypeAdapter(Parameter.class, new Parameter.ParamDeSerializer())
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create(responseGson))
                .build();
    }


    @OnClick(R.id.sign_login)
    public void login() {
        if (preventionClick() == true) {

            // 서버통신 테스트용 코드
            final String id = login_id.getText().toString();
            final String password = login_password.getText().toString();
            UserInfo userInfo = new UserInfo(id, password);

            RetrofitService service = retrofit.create(RetrofitService.class);
            service.login(userInfo).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        ServerResponse serverResponse = response.body();

                        if (serverResponse != null) {
                            Log.e("로그인  response->", "" + serverResponse.getMessage() + "," + serverResponse.getResultCode() + "," + serverResponse.getParam());

                            //Parameter parameter = (LoginResponse)(serverResponse.getParam());

                            if (serverResponse.getResultCode() == -1) {
                                Intent i = new Intent(LoginActivity.this, ProviderMainActivity.class);
                                Toast.makeText(LoginActivity.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                                startActivity(i);
                                finish();
                            } else {
                                Intent i = new Intent(LoginActivity.this, RequestMainActivity.class);

                                SharedPreferences sf = getSharedPreferences("Requester", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sf.edit();
                                editor.putString("id", id);
                                editor.commit();

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
            });
        }
    }



    @OnClick(R.id.sign_register)
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    //더블클릭 방지 함수
    private boolean preventionClick() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return false;
        } else {
            lastClickTime = SystemClock.elapsedRealtime();
            return true;
        }
    }

    //이미지 비트맵 줄여주기 함수
    private void modifyBitmap(int image) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        // inJustDecodeBounds = true일때 BitmapFactory.decodeResource는 리턴 x -> bitmap은 반환하지않고, options 변수에만 값이 대입
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), image, options);

        // 이미지 사이즈를 필요한 사이즈로 적당히 줄이기위해 계산한 값을 options.inSampleSize 에 2의 배수의 값으로 대입
        options.inSampleSize = setImageSize(options, REQUEST_WIDTH, REQUEST_HEIGHT);

        // options.inJustDecodeBounds 에 false 로 다시 설정해서 BitmapFactory.decodeResource의 Bitmap을 리턴받게 함
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image, options);

        // 이미지 size가 재설정된 이미지를 출력
        imageView.setImageBitmap(bitmap);
    }

    private int setImageSize(BitmapFactory.Options options, int requestWidth, int requestHeight) {

        //이미지 사이즈 원본에 대입
        int width = options.outWidth;
        int height = options.outHeight;

        //원본 이미지 비율 1로 초기
        int size = 1;

        //해상도 깨지지 않을 만큼의 요구되는 사이지 2로 나눠서 원본이미지를 나눔
        while (requestWidth < width || requestHeight < height) {
            width = width / 2;
            height = height / 2;

            size = size * 2;
        }

        return size;
    }
}
