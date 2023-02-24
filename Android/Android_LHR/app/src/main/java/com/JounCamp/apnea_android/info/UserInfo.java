package com.JounCamp.apnea_android.info;

import com.JounCamp.apnea_android.Role;

public class UserInfo {

    private Long id;

    private String username;

    private String password;

    private String realname;

    private String email;

    private String address;

    private String phoneNum;

    private String c_phoneNum;

    private Integer status;

    private String statusStartAt;

    private String statusEndAt;

    private Integer sign;

    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getC_phoneNum() {
        return c_phoneNum;
    }

    public void setC_phoneNum(String c_phoneNum) {
        this.c_phoneNum = c_phoneNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
