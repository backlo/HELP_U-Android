package com.example.help_u.Requester.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.help_u.EventBus.Getnoti;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.example.help_u.R;
import com.example.help_u.Requester.Data.LocationRequest;
import com.example.help_u.Requester.Service.MyServiceRequester;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class RequestMainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.help_btn)
    LinearLayout helpBtn;
    @BindView(R.id.call_btn)
    LinearLayout callBtn;
    @BindView(R.id.setting_btn)
    LinearLayout settingBtn;

    private static long lastClickTime = 0;

    static double lat = 0;
    static double lon = 0;
    Retrofit retrofit;

    SharedPreferences sp;
    public static final int REQUEST_CODE = 123;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_request_main);
        ButterKnife.bind(this);

        setPermissionLocation();
        locationService();

        sp = getSharedPreferences("Requester", MODE_PRIVATE);
        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.e("MyServiceRequester >> ", "service 시작");
        Intent intent = new Intent(getApplicationContext(), MyServiceRequester.class);
        startService(intent);

        locationService();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Getnoti event){
        Log.e("event bus",""+event.location);
    }

    //도움 요청 버튼
    @OnClick(R.id.help_btn)
    public void help_Btn() {

        if (preventionClick() == true) {
            if (lon == 0 || lat == 0) {
                Log.e("RequestMain Help >> ", "위도 경도 0");
                Toast.makeText(getApplicationContext(), "다시 보내주세요!", Toast.LENGTH_SHORT).show();
            } else {
                sendHelpRequest();
            }
        }

    }

    //119 버튼
    @OnClick(R.id.call_btn)
    public void call_119() {
        if (preventionClick() == true) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:119"));
            startActivity(intent);
        }
    }

    //setting 버튼
    @OnClick(R.id.setting_btn)
    public void goSettingBtn() {
        if (preventionClick() == true) {
            Intent intent = new Intent(RequestMainActivity.this, RequestSettingActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent serviceIntent = new Intent(getApplicationContext(), MyServiceRequester.class);
        stopService(serviceIntent);
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

    public void locationService() {

        Log.e("MainActiviy >> " ,"locationService시작");

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {

            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lon = location.getLongitude();
                    lat = location.getLatitude();
                    Log.e("MainActiviy >> " ,lon +","+lat);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        Log.e("RequestMain loc >> ", lat + "," + lon);

    }

    private void sendHelpRequest(){
        String id = sp.getString("id","");
        String message = sp.getString("message","");
        int count = sp.getInt("helpcount",0);

        if("".equals(message)){
            message="도와주세요!";
        }
        if(count == 0){
            count = 5;
        }

        Log.e("RequesterMain >> ", id +", " + message + ", " + count );
        LocationRequest locationRequest = new LocationRequest(""+lat+","+lon,id,message,count);

        RetrofitService service = retrofit.create(RetrofitService.class);
        service.sendLocation(locationRequest).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    Log.e("도움요청 to server","success!!!!!");
                    ServerResponse body = response.body();
                    if(body != null){
                        if(body.getResultCode() == 107){
                            Toast.makeText(getApplicationContext(), "이미 도움 요청 중입니다.", Toast.LENGTH_SHORT).show();
                        } else{
                            Intent i = new Intent(RequestMainActivity.this, RequestPopupActivity.class);
                            startActivity(i);
                        }

                        body.getMessage();
                        body.getParam();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.e("Permission >> ", "why not call");
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionDenied(this, String.valueOf(perms))){
            new AppSettingsDialog.Builder(this).build().show();
            Log.e("Permission >> ","onPermissionDenied!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("Permission >> ", "onActivityResult Method !!");
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){
            Log.e("Permission >> ", "onActivityResult!!");
        }
    }


}
