package com.example.help_u.Requester.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.help_u.R;
import com.example.help_u.Util.RetrofitService;
import com.example.help_u.model.HelperRegistration;
import com.example.help_u.model.ServerResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestEnrollFragment extends Fragment {

    @BindView(R.id.enroll_helper)
    Button enroll_helper;
    public RequestEnrollFragment() {
        // Required empty public constructor
    }
    Retrofit retrofit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_enroll, container, false);
        ButterKnife.bind(this, view);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://223.194.134.216:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return view;
    }

    @OnClick(R.id.enroll_helper)
    public void enrollHelper(){
        RetrofitService service = retrofit.create(RetrofitService.class);
        ArrayList<String> provider = new ArrayList<>();
        provider.add("01000001234");
        provider.add("01000001235");


        HelperRegistration helperRegistration = new HelperRegistration("test1",provider);
        service.helperRegistration(helperRegistration).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    ServerResponse serverResponse = response.body();
                    Log.e("제공자 등록 response->",""+serverResponse.getMessage()+","+serverResponse.getResultCode());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e("제공자 등록 error->",""+t.toString());
            }
        });

    }



}
