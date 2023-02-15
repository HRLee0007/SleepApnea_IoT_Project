package com.service.sleepapneaiotserver.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class WiFiConnectedFCM {
    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {

        private String token;

        private Notification notification;

    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String click_action;

    }
}
