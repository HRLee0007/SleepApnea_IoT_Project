package com.example.apnea_android;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apnea_android.activity.login;
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

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringㅋtone = RingtoneManager.getRingtone(getApplicationContext(), sound);

// pattern 을 진동의 패턴 -1은 패턴의 반복은 한번
        if(message.getNotification().getTitle() == "위험 1 : 진동") {
            vibe.vibrate(pattern, -1);
        }
        else if(message.getNotification().getTitle() == "위험 2 : 소리"){
            ringtone.play();
        }

        //푸쉬 알림 클릭 시, 바로 로그인 완료 화면으로 넘기는 부분
        Intent quickIntent = new Intent(this, login.class);
        quickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        quickIntent.putExtra() //메인에서 로그인 화면으로 값 넘길때 사용하면 됨
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, quickIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        startActivity(quickIntent);

        Log.d("Kim", "message = " + message.getNotification().getTitle());
    }



}
