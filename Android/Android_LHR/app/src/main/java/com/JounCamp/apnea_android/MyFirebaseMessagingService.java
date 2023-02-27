package com.JounCamp.apnea_android;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.JounCamp.apnea_android.activity.ChartActivity;
import com.JounCamp.apnea_android.activity.MeasureControlActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        long[] pattern = {0, 500, 200, 400, 100};

        Map<String, String> data = message.getData();
        String title = data.get("title");
        String body = data.get("body");
        String activity = data.get("activity");

        if(title.equals("초기 측정 시작")) {
            Log.d("kim", "초기 측정 시작");
            Intent intent = new Intent(MyFirebaseMessagingService.this, MeasureControlActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("statusTitle", "초기 측정 시작");
            startActivity(intent);
        }

        if(title.equals("위험 0 : 정상 호흡")) {
            Log.d("kim", "sign0 일때 정상 호흡");
            Intent intent = new Intent(MyFirebaseMessagingService.this, MeasureControlActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("statusTitle", "정상 호흡");
            startActivity(intent);
        }

        Log.d("Kim", "onMessageReceived title" + title);

        if(title.equals("위험 1 : 진동")) {
            Log.d("Kim", "진동 타이틀");

            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            VibrationEffect effect = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                effect = VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE);
            }

            // Vibrate the device
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(effect);
            } else {
                vibrator.vibrate(pattern, -1);
            }

            Log.d("kim", "sign1 일때 진동 울림");
            Intent intent = new Intent(MyFirebaseMessagingService.this, MeasureControlActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("statusTitle", "진동");
            startActivity(intent);
        }
        else if(title.equals("위험 2 : 소리")){

            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert_sound1);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), soundUri);


            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build();

            ringtone.setAudioAttributes(audioAttributes);
            ringtone.play();

            Log.d("kim", "sign2 일때 소리 울림");
            Intent intent = new Intent(MyFirebaseMessagingService.this, MeasureControlActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("statusTitle", "소리");
            startActivity(intent);
        }

        if (title.equals("초기측정 완료")) {
            Log.d("kim", "sign5 일때 초기측정 완료");
            Intent intent = new Intent(MyFirebaseMessagingService.this, MeasureControlActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("statusTitle", "초기측정 완료");
            startActivity(intent);
        }

        if(activity.equals("measure")) {

            //푸쉬 알림 클릭 시, 바로 로그인 완료 화면으로 넘기는 부분
            Intent quickIntent = new Intent(this, MeasureControlActivity.class);
            quickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, quickIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            startActivity(quickIntent);

            Log.d("Kim", "measure message = " + message.getNotification().getTitle());
        }


    }


    private void sendNotification(String title, String body) {

        Intent intent = new Intent(this, MeasureControlActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert_sound1);
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), soundUri);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setVibrate(new long[]{1000, 1000})
                .setLights(Color.BLUE,1,1)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}