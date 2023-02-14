package com.example.apnea_android;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.apnea_android.activity.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Arrays;
import java.util.Map;

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

        Map<String, String> data = message.getData();
        String title = data.get("title");
        String body = data.get("body");
//        String vibrate = data.get("vibrate");
//        String sound = data.get("sound");
//
//        Uri soundUri = Uri.parse(sound);
//        Ringtone ringtone = RingtoneManager.getRingtone(this, soundUri);

        Log.d("Kim", "onMessageReceived title" + title);

//        long[] vib_pattern = Arrays.stream(vibrate.split(","))
//                .mapToLong(Long::parseLong)
//                .toArray();

//        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), sound);

// pattern 을 진동의 패턴 -1은 패턴의 반복은 한번
//        if(message.getNotification().getTitle() == "위험 1 : 진동") {
        if(title.equals("위험 1 : 진동")) {
            Log.d("Kim", "진동 타이틀");
            VibrationEffect effect = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                effect = VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE);
            }

            // Get the system vibration service
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            // Vibrate the device
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(effect);
            } else {
                vibrator.vibrate(pattern, -1);
            }


            // Show the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, builder.build());
        }
//        else if(message.getNotification().getTitle() == "위험 2 : 소리"){
        else if(title.equals("위험 2 : 소리")){

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build();

            ringtone.setAudioAttributes(audioAttributes);
            ringtone.play();

            /*// Play the sound
            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert_sound1);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), soundUri);
            ringtone.play();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, builder.build());*/
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