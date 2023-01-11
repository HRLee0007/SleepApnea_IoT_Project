package com.service.sleepapneaiotserver.web.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UpdateResponseDto {

    private String username;
    private String realname;
    private String email;
    private String address;
    private String phoneNum;
    private String c_phoneNum;

}
