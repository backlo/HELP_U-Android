package com.example.help_u.Provider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.help_u.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderMainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    @BindView(R.id.main_provider_setting_btn)
    Button setting_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

    }

    @OnClick(R.id.main_provider_setting_btn)
    public void goSetting(){
        Intent intent = new Intent(ProviderMainActivity.this, ProviderSettingActivity.class );
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng seoul = new LatLng(37.52487, 126.92723);

        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(seoul)
                .title("원하는 위치(위도, 경도)에 마커를 표시했습니다.");

        // 마커를 생성한다.
        mMap.addMarker(makerOptions);

        //카메라를 여의도 위치로 옮긴다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));


    }
}
