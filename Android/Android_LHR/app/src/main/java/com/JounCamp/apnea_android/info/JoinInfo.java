package com.JounCamp.apnea_android.info;

import com.JounCamp.apnea_android.Role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

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
