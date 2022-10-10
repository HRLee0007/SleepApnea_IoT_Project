package com.service.sleepapneaiotserver.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Getter
@NoArgsConstructor

public class testDom {

    private int time;
    private int bpmCount;
    private int o2Count;
    private int noisyCount;

    @Builder
    public testDom(int time, int bpmCount, int o2Count, int noisyCount){
        this.time = time;
        this.bpmCount = bpmCount;
        this.o2Count = o2Count;
        this.noisyCount = noisyCount;
    }

    public int getTime() {
        return time;
    }

    public int getBpmCount() {
        return bpmCount;
    }

    public int getO2Count() {
        return o2Count;
    }

    public int getNoisyCount() {
        return noisyCount;
    }
}
