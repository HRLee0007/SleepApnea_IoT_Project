package com.service.sleepapneaiotserver.web.dto;

import com.service.sleepapneaiotserver.domain.user.Role;
import com.service.sleepapneaiotserver.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSessionDto implements Serializable {
    private String username;
    private String password;
    private String realname;
    private String email;
    private Role role;
    private Integer status;

    public UserSessionDto(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.realname = user.getRealname();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.status = user.getStatus();
    }
}
