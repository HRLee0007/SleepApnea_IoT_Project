package com.example.sleepiot;

import com.example.sleepiot.Role;
import com.google.gson.annotations.SerializedName;

public class JoinInfo {

    //@SerializedName("username")
    private String username;

    //@SerializedName("password")
    private String password;

    //@SerializedName("realname")
    private String realname;

    //@SerializedName("email")
    private String email;

    //@SerializedName("address")
    private String address;

    //@SerializedName("phoneNum")
    private String phoneNum;

    //@SerializedName("c_phoneNum")
    private String c_phoneNum;

    //@SerializedName("role")
    private Role role;


    public JoinInfo(String username, String password, String realname, String email, String address, String phoneNum, String c_phoneNum, Role role) {
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
        this.c_phoneNum = c_phoneNum;
        this.role = role;
    }

    @Override
    public String toString() {
        return "JoinInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realname='" + realname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", c_phoneNum='" + c_phoneNum + '\'' +
                ", role=" + role +
                '}';
    }
}
