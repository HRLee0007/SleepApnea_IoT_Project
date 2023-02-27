package com.service.sleepapneaiotserver.fcm.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.google.gson.JsonParseException;
import com.service.sleepapneaiotserver.fcm.dto.WiFiConnectedFCM;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.service.sleepapneaiotserver.fcm.dto.FcmMessage;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FCMService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/sleepapnea-24608/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {

        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build())
                        .data(FcmMessage.Data.builder()
                                .title(title)
                                .body(body)
//                                .sound("alert_sound1.mp3")
                                .activity("measure")
                                .click_action("push_activity")
                                .build())
                        .android(FcmMessage.Android.builder()
                                .ttl("1s")
                                .priority("high")
                                .notification(FcmMessage.AndroidNotification.builder()
                                        .click_action("push_activity")
                                        .sound("alert_sound1")
                                        .build())
                                .build())
                        .build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    public void sendMessageTo2(String targetToken) throws IOException {
        String message = makeMessage2(targetToken);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    private String makeMessage2(String targetToken) throws JsonParseException, JsonProcessingException {

        WiFiConnectedFCM fcmMessage = WiFiConnectedFCM.builder()
                .message(WiFiConnectedFCM.Message.builder()
                        .token(targetToken)
                        .notification(WiFiConnectedFCM.Notification.builder()
                                .title("WiFi 연결 완료")
                                .body("클릭 시 메인 페이지로 이동합니다.")
                                .image(null)
                                .build())
                        .data(WiFiConnectedFCM.Data.builder()
                                .title("WiFi 연결 완료")
                                .body("클릭 시 메인 페이지로 이동합니다.")
                                .activity("measure")
                                .build())
                        .android(WiFiConnectedFCM.Android.builder()
                                .ttl("1s")
                                .notification(WiFiConnectedFCM.AndroidNotification.builder()
                                        .click_action("wifi_connected")
                                        .build())
                                .build())
                        .build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    public void sendMessageTo3(String targetToken) throws IOException {
        String message = makeMessage3(targetToken);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    private String makeMessage3(String targetToken) throws JsonParseException, JsonProcessingException {

        WiFiConnectedFCM fcmMessage = WiFiConnectedFCM.builder()
                .message(WiFiConnectedFCM.Message.builder()
                        .token(targetToken)
                        .notification(WiFiConnectedFCM.Notification.builder()
                                .title("초기측정 완료")
                                .body("클릭 시 측정 페이지로 이동합니다.")
                                .image(null)
                                .build())
                        .data(WiFiConnectedFCM.Data.builder()
                                .title("초기측정 완료")
                                .body("클릭 시 측정 페이지로 이동합니다.")
                                .activity("measure")
                                .build())
                        .android(WiFiConnectedFCM.Android.builder()
                                .ttl("1s")
                                .notification(WiFiConnectedFCM.AndroidNotification.builder()
                                        .click_action("wifi_connected")
                                        .build())
                                .build())
                        .build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}