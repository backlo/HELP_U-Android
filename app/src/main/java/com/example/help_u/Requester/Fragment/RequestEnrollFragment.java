package com.example.help_u.Requester.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.help_u.Requester.Data.HelperRegistration;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.example.help_u.R;
import com.example.help_u.Requester.Adapter.EnrollAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//연락처 등록 부분
public class RequestEnrollFragment extends Fragment {

    @BindView(R.id.enroll_add)
    Button add;
    @BindView(R.id.enroll_del)
    Button del;
    @BindView(R.id.enroll_commit)
    Button enrollCommit;
    @BindView(R.id.enroll_cancel)
    Button enrollCancel;
    @BindView(R.id.enroll_listview)
    ListView listView;

    EnrollAdapter enrollAdapter;

    private SharedPreferences sp;

    public RequestEnrollFragment() {
        enrollAdapter = new EnrollAdapter();
    }

    Retrofit retrofit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_enroll, container, false);
        ButterKnife.bind(this, v);
        listView.setAdapter(enrollAdapter);

        sp = getActivity().getSharedPreferences("Requester", Activity.MODE_PRIVATE);

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return v;
    }

    @OnClick(R.id.enroll_add)
    public void addPhone(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, 0);

    }

    @OnClick(R.id.enroll_del)
    public void delPhone(){
        enrollAdapter.deleteItem();
    }

    @OnClick(R.id.enroll_commit)
    public void enrollCommit(){

    }

    @OnClick(R.id.enroll_cancel)
    public void enrollCancel(){
        getActivity().onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String[] phonepro = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Cursor cursor = getActivity().getContentResolver().query(data.getData(), phonepro, null, null, null);
        cursor.moveToFirst();

        String name = cursor.getString(0);
        String number = cursor.getString(1);

        cursor.close();

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",name);
        editor.putString("number",number);

        editor.commit();

        RetrofitService service = retrofit.create(RetrofitService.class);
        ArrayList<String> provider = new ArrayList<>();

        provider.add(number);

        String id = sp.getString("id", "");
        Log.e("EnrollFragment >> ", id);

        HelperRegistration helperRegistration = new HelperRegistration(id,provider);
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

        super.onActivityResult(requestCode,resultCode,data);
    }

}