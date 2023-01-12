package com.service.sleepapneaiotserver.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.sleepapneaiotserver.domain.testDom;
import com.service.sleepapneaiotserver.web.dto.TestRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(testController.class)
public class testControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void hello_test() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"))
                .andDo(print());
    }
    @Test
    public void hello_포스트방식() throws Exception{
        String content = objectMapper.writeValueAsString(new TestRequestDto(100, 101));

        mockMvc.perform(post("/hello/post")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("100is101"))
                .andDo(print());

    }
}
//"맥박수 : " + info.getBpmCount() + "\n" +
//        "산소포화도 : " + info.getO2Count() + "\n" +
//        "코골이 : " + info.getNoisyCount() +"\n"+
//        "시간 : " + info.getTime() + "\n";