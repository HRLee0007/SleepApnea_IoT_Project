package com.service.sleepapneaiotserver.fcm.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification;
        private String token;

        private Android android;

    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;

    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Android {
        private String ttl;

        private AndroidNotification notification;

    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class AndroidNotification {
        private String sound;
        private String click_action;

    }

}