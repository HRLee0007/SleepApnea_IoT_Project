package com.example.apnea_android.info;

public class MeasureRequestInfo {

    private String username;

    private Integer status;

    public MeasureRequestInfo(String username, Integer status) {
        this.username = username;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MeasureRequestInfo{" +
                "username='" + username + '\'' +
                ", status=" + status +
                '}';
    }
}
