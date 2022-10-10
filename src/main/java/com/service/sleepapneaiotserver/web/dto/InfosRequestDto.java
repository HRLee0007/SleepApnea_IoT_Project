package com.service.sleepapneaiotserver.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InfosRequestDto {

    private int pulse;
    private double o2;
    private String username;



}
