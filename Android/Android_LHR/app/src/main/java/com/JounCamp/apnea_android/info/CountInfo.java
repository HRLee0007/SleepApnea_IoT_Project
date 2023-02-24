package com.JounCamp.apnea_android.info;

import java.sql.Timestamp;

public class CountInfo {

    private Long id;

    private int count;

    private UserInfo user;

    private Timestamp createDate;

    private String statusStartAt;

    private String statusEndAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getStatusStartAt() {
        return statusStartAt;
    }

    public void setStatusStartAt(String statusStartAt) {
        this.statusStartAt = statusStartAt;
    }

    public String getStatusEndAt() {
        return statusEndAt;
    }

    public void setStatusEndAt(String statusEndAt) {
        this.statusEndAt = statusEndAt;
    }
}
