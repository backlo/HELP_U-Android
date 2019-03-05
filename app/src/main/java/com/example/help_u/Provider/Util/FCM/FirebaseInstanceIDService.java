package com.example.help_u.Provider.Util.FCM;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("fcm", "Refreshed token: " + refreshedToken);

        Log.e("fcm",""+FirebaseInstanceId.getInstance().getInstanceId());
        SharedPreferences sp = getSharedPreferences("Requester", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("token",refreshedToken);
        editor.commit();
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
    }

}
