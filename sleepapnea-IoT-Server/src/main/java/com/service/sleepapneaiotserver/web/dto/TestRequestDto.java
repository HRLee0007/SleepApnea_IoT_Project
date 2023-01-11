package com.service.sleepapneaiotserver.web.dto;


import com.service.sleepapneaiotserver.domain.testDom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class TestRequestDto {
    private int time;
    private int bpmCount;

    @Builder
    public TestRequestDto(int time, int bpmCount) {
        this.time = time;
        this.bpmCount = bpmCount;
    }
}
