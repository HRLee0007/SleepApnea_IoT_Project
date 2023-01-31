package com.example.apnea_android.info;

public class LoginInfo {
    //@SerializedName("username")
    private String username;

    //@SerializedName("password")
    private String password;

    private String token;

    public LoginInfo(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
