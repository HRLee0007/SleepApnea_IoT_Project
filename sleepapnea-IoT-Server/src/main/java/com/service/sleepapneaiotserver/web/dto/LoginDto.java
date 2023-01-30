package com.service.sleepapneaiotserver.web.dto;

import com.service.sleepapneaiotserver.domain.user.UserToken;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class LoginDto implements Serializable {

    private String username;
    private String password;
    private String token;

    public UserToken toEntity() {
        UserToken userToken = UserToken.builder()
                .username(username)
                .password(password)
                .token(token)
                .build();
        return userToken;
    }




}
