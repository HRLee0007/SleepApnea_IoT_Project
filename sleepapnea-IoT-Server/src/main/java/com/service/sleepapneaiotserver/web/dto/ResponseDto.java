package com.service.sleepapneaiotserver.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  //getter setter
@NoArgsConstructor // default 생성자
@AllArgsConstructor // 생성자
@Builder
public class ResponseDto<T> {
    int status;
    T data;
}