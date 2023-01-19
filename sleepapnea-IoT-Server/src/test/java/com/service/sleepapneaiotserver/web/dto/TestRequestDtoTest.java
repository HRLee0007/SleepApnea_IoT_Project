package com.service.sleepapneaiotserver.web.dto;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRequestDtoTest {


    @Test
    public void 롬북_테스트(){

        // given
        int time = 100;
        int bpmCount = 101;

        //when
        TestRequestDto dto = new TestRequestDto(time, bpmCount);


        //then
        Assertions.assertThat(dto.getTime()).isEqualTo(time);
        Assertions.assertThat(dto.getBpmCount()).isEqualTo(bpmCount);
    }

}