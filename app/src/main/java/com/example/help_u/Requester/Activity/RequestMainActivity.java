package com.example.help_u.Requester.Activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.help_u.Provider.Data.Parameter;
import com.example.help_u.Provider.Data.RequestHelp;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.example.help_u.R;
import com.example.help_u.Requester.Data.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
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

public class RequestMainActivity extends AppCompatActivity {

    @BindView(R.id.help_btn)
    LinearLayout helpBtn;
    @BindView(R.id.call_btn)
    LinearLayout callBtn;
    @BindView(R.id.setting_btn)
    LinearLayout settingBtn;

    private static long lastClickTime = 0;

    static double lat = 0;
    static double lon = 0;
    LocationManager lm;
    Retrofit retrofit;

    @Override
    protected void onStart() {
        super.onStart();
        locationService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_request_main);
        ButterKnife.bind(this);


        final Gson responseGson = new GsonBuilder()
                .registerTypeAdapter(Parameter.class, new Parameter.ParamSerializer())
                .registerTypeAdapter(Parameter.class, new Parameter.ParamDeSerializer())
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create(responseGson))
                .build();
    }

    //도움 요청 버튼
    @OnClick(R.id.help_btn)
    public void help_Btn() {

        if (preventionClick() == true) {
            Intent i = new Intent(RequestMainActivity.this, RequestPopupActivity.class);
            startActivity(i);
        }

        if(lon == 0 || lat == 0){
            Log.e("RequestMain Help >> " , "위도 경도 0");
        } else{
            sendHelpRequest();
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

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lon = location.getLongitude();
                    lat = location.getLatitude();
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
        LocationRequest locationRequest = new LocationRequest(lat,lon);
        RetrofitService service = retrofit.create(RetrofitService.class);
        service.sendlocation(locationRequest).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    ServerResponse body = response.body();
                    if(body != null){
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

}
