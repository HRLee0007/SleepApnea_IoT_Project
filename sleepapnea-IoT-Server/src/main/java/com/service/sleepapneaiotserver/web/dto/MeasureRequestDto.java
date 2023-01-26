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
public class MeasureRequestDto implements Serializable {

    private String username;
    private Integer status;

    public MeasureRequestDto(User user){
        this.username = user.getUsername();
        this.status = user.getStatus();
    }




}