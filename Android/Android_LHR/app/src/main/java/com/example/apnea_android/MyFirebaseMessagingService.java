package com.example.apnea_android;

import android.content.Context;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onNewToken(String token){
        System.out.println("MyFirebaseMessagingService.onNewToken");
        Log.d("kim", "start token");
        Log.d("Kim", "Refreshed token: "+token);
        Log.d("kim", "end token");
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        long[] pattern = {0, 500, 200, 400, 100};
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// pattern 을 진동의 패턴 -1은 패턴의 반복은 한번
        vibe.vibrate(pattern, -1);

        Log.d("Kim", "message = " + message.getNotification().getTitle());
        
    }
}
