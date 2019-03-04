package com.example.help_u.Provider.Util.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.help_u.Provider.ProviderMainActivity;
import com.example.help_u.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;

public class FireBaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        Log.e("fcm 토큰", "Refreshed token: " + token);
        super.onNewToken(token);
    }


    private void sendNotification(String messageBody, String title) {
        Intent intent = new Intent(this, ProviderMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //foreground notification
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = null;
        try {
            notificationBuilder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.noti_test)
                    .setContentTitle(URLDecoder.decode(title, "UTF-8"))
                    .setContentText(URLDecoder.decode(messageBody, "UTF-8"))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("fcm", "onMessageReceived! fcm 받음" + remoteMessage.getData().get("location"));

        try {
            sendNotification(URLDecoder.decode(remoteMessage.getNotification().getBody(), "UTF-8"), URLDecoder.decode(remoteMessage.getNotification().getTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String data_location = remoteMessage.getData().get("location");
        Log.e("noti data", "" + data_location);
        Log.e("noti data->getbody", "" + remoteMessage.getData());
        Log.e("noti data->gettitle", "" + remoteMessage.getNotification().getBody());

        getCurrentAddress(data_location);

        super.onMessageReceived(remoteMessage);
    }

    public String getCurrentAddress(String latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        String locData[] = latlng.split(",");
        double lat = Double.parseDouble(locData[0]);
        double lon = Double.parseDouble(locData[1]);

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    lat,
                    lon,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            Log.e("noti", "" + address.getAddressLine(0));
            return address.getAddressLine(0);
        }
    }
}
