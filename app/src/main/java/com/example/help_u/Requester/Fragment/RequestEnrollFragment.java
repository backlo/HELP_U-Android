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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.help_u.Requester.Data.Response.ProviderPhonesResponse;
import com.example.help_u.Provider.Data.ServerResponse;
import com.example.help_u.Provider.Util.Retrofit.RetrofitService;
import com.example.help_u.R;
import com.example.help_u.Requester.Data.AddNumber;
import com.example.help_u.Requester.Data.HelpRegistration;
import com.example.help_u.Requester.Data.RemoveNumber;
import com.google.gson.Gson;

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

    private SharedPreferences sp;
    private ArrayList<String> items;
    private ArrayAdapter adapter;

    public RequestEnrollFragment() {

    }

    Retrofit retrofit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_enroll, container, false);
        ButterKnife.bind(this, v);

        items = new ArrayList<String>() ;
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_single_choice, items);

        sp = getActivity().getSharedPreferences("Requester", Activity.MODE_PRIVATE);
        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        enrolledNumber();
        return v;
    }

    @OnClick(R.id.enroll_add)
    public void addPhone() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, 0);

    }

    @OnClick(R.id.enroll_del)
    public void delPhone() {
        int count, checked;

        count = adapter.getCount();

        if(count>0){
            checked = listView.getCheckedItemPosition();
            String number = (String) adapter.getItem(checked);

            String id = sp.getString("id", "");

            final RetrofitService service = retrofit.create(RetrofitService.class);
            final RemoveNumber removeNumber = new RemoveNumber(id, number);
            service.removeNumber(removeNumber).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        ServerResponse serverResponse = response.body();
                        Log.e("제공자 등록 response->", "" + serverResponse.getMessage() + "," + serverResponse.getResultCode());

                            Toast.makeText(getContext(), "삭제 하였습니다.", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e("제공자 등록 error->", "" + t.toString());
                }
            });

            if (checked > -1 && checked < count) {
                items.remove(checked) ;
                listView.clearChoices();
                adapter.notifyDataSetChanged();
            }
        }

    }

    @OnClick({R.id.enroll_commit, R.id.enroll_cancel})
    public void enrollCommit() {
        getFragmentManager().beginTransaction().replace(R.id.setting_fragment, new RequestUserInfoFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String[] phonepro = new String[]{
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Cursor cursor = getActivity().getContentResolver().query(data.getData(), phonepro, null, null, null);
        cursor.moveToFirst();

        final String number = cursor.getString(0);

        cursor.close();

        String id = sp.getString("id", "");

        final RetrofitService service = retrofit.create(RetrofitService.class);
        final AddNumber addNumber = new AddNumber(id, number);
        service.addNumber(addNumber).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    ServerResponse serverResponse = response.body();
                    Log.e("제공자 등록 response->", "" + serverResponse.getMessage() + "," + serverResponse.getResultCode());

                    if(serverResponse.getResultCode() != 110){
                        items.add(number);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(),"등록 하였습니다.",Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getContext(),"등록된 번호입니다.",Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("제공자 등록 error->", "" + t.toString());
            }
        });

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void enrolledNumber() {

        final RetrofitService service = retrofit.create(RetrofitService.class);

        String id = sp.getString("id", "");
        Log.e("EnrollFragment >> ", id);

        final HelpRegistration helpRegistration = new HelpRegistration(id);
        service.helperRegistration(helpRegistration).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    ServerResponse serverResponse = response.body();

                    Log.e("제공자 등록 response->", "" + serverResponse.getMessage() + "," + serverResponse.getResultCode());
                    Gson gson = new Gson();
                    String json = gson.toJson(serverResponse.getParam()); // May not serialize foo.value correctly
                    ProviderPhonesResponse phonesResponse = gson.fromJson(json, ProviderPhonesResponse.class); // Fails to deserialize foo.value as Bar

                    for (int i = 0; i < phonesResponse.getPhones().size(); i++) {
                        Log.e("EnrollFragment >> ", "" + phonesResponse.getPhones().get(i));
                        items.add(phonesResponse.getPhones().get(i));
                    }
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("제공자 등록 error->", "" + t.toString());
                Toast.makeText(getContext(), "서버에 연결할수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}