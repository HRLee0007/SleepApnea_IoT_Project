package com.example.apnea_android.fcm;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apnea_android.activity.login;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (message.getNotification() != null) {
            Log.d("kim", "알림 메시지: " + message.getNotification().getBody());
            String messageBody = message.getNotification().getBody();
            String messageTitle = message.getNotification().getTitle();
            Intent intent = new Intent(this, login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("kim", "Refreshed token: " + token);
    }
}
