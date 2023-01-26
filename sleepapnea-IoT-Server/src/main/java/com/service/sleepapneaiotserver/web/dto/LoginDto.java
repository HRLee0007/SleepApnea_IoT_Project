package com.service.sleepapneaiotserver.web.dto;

import com.service.sleepapneaiotserver.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LoginDto implements Serializable {

    private String username;
    private String password;

    public LoginDto(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
    }




}
