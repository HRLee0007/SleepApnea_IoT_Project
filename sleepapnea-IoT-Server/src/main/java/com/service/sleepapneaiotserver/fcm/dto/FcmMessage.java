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

        private String token;

        private Notification notification;

        private Data data;

        private Android android;

    }
//
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;

//        private String sound;



    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data {

        private String title;

        private String body;

        private String sound;

        private String activity;

        private String click_action;
    }
//
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Android {
        private String ttl;

        private AndroidNotification notification;

        private String priority;

    }
//
    @Builder
    @AllArgsConstructor
    @Getter
    public static class AndroidNotification {
        private String click_action;

        private String sound;

    }

}