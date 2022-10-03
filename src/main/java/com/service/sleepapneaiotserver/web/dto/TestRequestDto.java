package com.service.sleepapneaiotserver.web.dto;


import com.service.sleepapneaiotserver.domain.testDom;
import lombok.Getter;

@Getter
public class TestRequestDto {
    private int time;
    private int bpmCount;
    private int o2Count;
    private int noisyCount;
    
    public TestRequestDto(testDom test){
        this.time = test.getTime();
        this.bpmCount = test.getBpmCount();
        this.o2Count = test.getO2Count();
        this.noisyCount = test.getNoisyCount();
    }
}
