package com.example.help_u;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.help_u.Provider.Data.Response.LoginResponse;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Data.UserInfo;
import com.example.help_u.Provider.ProviderMainActivity;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.example.help_u.Requester.Activity.RequestMainActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
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

    @BindView(R.id.login_linear)
    LinearLayout login_linear;

    InputMethodManager inputMethodManager;

    private Retrofit retrofit;
    private UserInfo userInfo;
    String refreshedToken;
    SharedPreferences sp;

    private final int REQUEST_WIDTH = 512;
    private final int REQUEST_HEIGHT = 512;
    String gettoken = "";
    public static final int REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        setPermissionLocation();

        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("refreshedToken",""+refreshedToken);

        modifyBitmap(R.drawable.main);

        userInfo = new UserInfo();

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        login_password.setFocusableInTouchMode(true);
        LoginActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        inputMethodManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
    }

    @OnClick(R.id.login_linear)
    public void hideKeyboard(){
        inputMethodManager.hideSoftInputFromWindow(login_password.getWindowToken(), 0);
    }

    //로그인 버튼 서버로 아이디 비밀번호 보내서 맞는지 확인
    @OnClick(R.id.sign_login)
    public void login() {
        sp = getSharedPreferences("Requester", Activity.MODE_PRIVATE);
        gettoken = sp.getString("token", "");
        Log.e("loginactivity",""+gettoken);

        if (preventionClick() == true && gettoken != null) {
            // 서버통신 테스트용 코드
            String id = login_id.getText().toString();
            String password = login_password.getText().toString();
            final UserInfo userInfo = new UserInfo(id, password,refreshedToken);

            RetrofitService service = retrofit.create(RetrofitService.class);
            service.login(userInfo).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        ServerResponse serverResponse = response.body();

                        if (serverResponse != null) {
                            Log.e("로그인  response->", "" + serverResponse.getMessage() + "," + serverResponse.getResultCode());
                            Gson gson = new Gson();
                            String json = gson.toJson(serverResponse.getParam());
                            LoginResponse loginResponse = gson.fromJson(json, LoginResponse.class);

                            if (serverResponse.getResultCode() == 0 ) {
                                if("provider".equals(loginResponse.getUser_type())){
                                    Intent i = new Intent(LoginActivity.this, ProviderMainActivity.class);
                                    Toast.makeText(LoginActivity.this, "로그인 완료", Toast.LENGTH_SHORT).show();

                                    Log.e("LoginActivity >> " , loginResponse.getUser_type() + userInfo.getId() +"");

                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("id", userInfo.getId());
                                    editor.putString("usertype",loginResponse.getUser_type());
                                    editor.commit();

                                    startActivity(i);
                                    finish();
                                }

                                else if("requester".equals(loginResponse.getUser_type())){
                                    Intent i = new Intent(LoginActivity.this, RequestMainActivity.class);

                                    Log.e("LoginActivity >> " , loginResponse.getUser_type() + userInfo.getId() +"");

                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("id", userInfo.getId());
                                    editor.putString("usertype",loginResponse.getUser_type());
                                    editor.commit();

                                    Toast.makeText(LoginActivity.this, "" + serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    startActivity(i);
                                    finish();
                                }

                            }else {
                                Toast.makeText(LoginActivity.this, serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    //회원가입버튼
    @OnClick(R.id.sign_register)
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    //시작할때 자동 로그인 되게 하는 함수
    @Override
    protected void onStart() {
        super.onStart();
        sp = getSharedPreferences("Requester", MODE_PRIVATE);
        Log.e("LoginActivity >> ", sp.getString("usertype",""));
        if(!"".equals(sp.getString("id",""))){
            if("requester".equals(sp.getString("usertype",""))){
                Intent i = new Intent(LoginActivity.this, RequestMainActivity.class);
                Toast.makeText(LoginActivity.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(LoginActivity.this, ProviderMainActivity.class);
                Toast.makeText(LoginActivity.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }

        }
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

    //위치,주소록 퍼미션 체크부분
    private void setPermissionLocation(){
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS};

        if(EasyPermissions.hasPermissions(this, perms)){
            Log.e("Permission >> " , "Location Checked");
        } else{
            EasyPermissions.requestPermissions(this,"권한 허용 부탁드립니다.", REQUEST_CODE, perms);
        }
    }

    //위치,주소록 퍼미션 체크부분
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //위치,주소록 퍼미션 체크부분
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.e("Permission >> ", "why not call");
    }

    //위치,주소록 퍼미션 체크부분
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionDenied(this, String.valueOf(perms))){
            new AppSettingsDialog.Builder(this).build().show();
            Log.e("Permission >> ","onPermissionDenied!");
        }
    }

    //위치,주소록 퍼미션 체크부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("Permission >> ", "onActivityResult Method !!");
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){
            Log.e("Permission >> ", "onActivityResult!!");
        }
    }
}
