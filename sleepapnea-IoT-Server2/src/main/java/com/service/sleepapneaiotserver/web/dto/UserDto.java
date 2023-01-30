package com.service.sleepapneaiotserver.web.dto;


import com.service.sleepapneaiotserver.domain.user.Role;
import com.service.sleepapneaiotserver.domain.user.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {

    private String username;
    private String password;
    private String realname;
    private String email;
    private String address;
    private String phoneNum;
    private String c_phoneNum;
    private Role role;

    public User toEntity(){
        User user = User.builder()
                .username(username)
                .password(password)
                .realname(realname)
                .email(email)
                .address(address)
                .phoneNum(phoneNum)
                .c_phoneNum(c_phoneNum)
//                .role(Role.USER)
                .role(role)
                .build();
        return user;
    }
}
