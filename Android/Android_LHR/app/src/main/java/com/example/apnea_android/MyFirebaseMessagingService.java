package com.example.apnea_android;

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
import android.os.Handler;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.apnea_android.activity.MainActivity;
import com.example.apnea_android.activity.MeasureControlActivity;
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


//        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
//        wakeLock = powerManager.newWakeLock(
//                PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
//                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
//                        PowerManager.ON_AFTER_RELEASE,
//                "WAKELOCK");
//
//        wakeLock.acquire();
//
//        wakeLock.release();




        long[] pattern = {0, 500, 200, 400, 100};

        Map<String, String> data = message.getData();
        String title = data.get("title");
        String body = data.get("body");
        String activity = data.get("activity");

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
//            Toast toast = Toast.makeText(MeasureControlActivity.class , "무호흡 발생 : 10초 경과", Toast.LENGTH_SHORT);
//            toast.show();
//
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    toast.cancel();
//                }
//            }, 1000);

            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            VibrationEffect effect = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                effect = VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE);
            }

            // Get the system vibration service

            // Vibrate the device
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(effect);
            } else {
                vibrator.vibrate(pattern, -1);
            }
//
//
//            // Show the notification
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "0")
//                    .setSmallIcon(R.drawable.notification_icon)
//                    .setContentTitle(title)
//                    .setContentText(body)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH);

//            wakeLock.acquire(3000);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//            notificationManager.notify(0, builder.build());
        }
//        else if(message.getNotification().getTitle() == "위험 2 : 소리"){
        else if(title.equals("위험 2 : 소리")){

//            wakeLock.acquire(3000);
//            Toast toast = Toast.makeText(MyFirebaseMessagingService.this, "무호흡 발생 : 15초 경과", Toast.LENGTH_SHORT);
//            toast.show();
//
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    toast.cancel();
//                }
//            }, 1000);

            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert_sound1);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), soundUri);


            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build();

            ringtone.setAudioAttributes(audioAttributes);
            ringtone.play();

//            sendNotification(title, body);

            /*// Play the sound
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
            ringtone.play();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, builder.build());*/
        }


        if(activity.equals("measure")) {

            //푸쉬 알림 클릭 시, 바로 로그인 완료 화면으로 넘기는 부분
            Intent quickIntent = new Intent(this, MeasureControlActivity.class);
            quickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            if(title.equals("WiFi 연결 완료")){
//                quickIntent.putExtra("toast", "wifi");
//            }
//            else{
//                quickIntent.putExtra("toast", "wifi_no");
//
//            }
//        quickIntent.putExtra() //메인에서 로그인 화면으로 값 넘길때 사용하면 됨

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, quickIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            startActivity(quickIntent);

            Log.d("Kim", "message = " + message.getNotification().getTitle());
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