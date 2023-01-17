package com.example.sleepiot;

public class LoginInfo {
    //@SerializedName("username")
    private String username;

    //@SerializedName("password")
    private String password;


    public LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
