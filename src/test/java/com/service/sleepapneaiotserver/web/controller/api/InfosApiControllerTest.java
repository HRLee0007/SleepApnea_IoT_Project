package com.service.sleepapneaiotserver.web.controller.api;

import com.service.sleepapneaiotserver.domain.infos.Infos;
import com.service.sleepapneaiotserver.domain.infos.InfosRepository;
import com.service.sleepapneaiotserver.domain.user.User;
import com.service.sleepapneaiotserver.domain.user.UserRepository;
import com.service.sleepapneaiotserver.web.dto.InfosRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InfosApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InfosRepository infosRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void Infos_저장하기() throws Exception{
        int pulse = 200;
        double o2 = 98.5;
        String username = "alstjr";
        userRepository.save(User.builder()
                .username("alstjr")
                .password("111111")
                .email("tjalstjr111@naver.com")
                .build());
        Optional<User> user = userRepository.findById(1L);


        InfosRequestDto requestDto = new InfosRequestDto(pulse, o2, user.get().getUsername());
        String url = "http://localhost:" + port + "/api/v1/info";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Infos> all = infosRepository.findAll();
        Assertions.assertThat(all.get(0).getPulse()).isEqualTo(pulse);
        Assertions.assertThat(all.get(0).getO2()).isEqualTo(o2);

    }
    @Test
    public void Infos_데이터받기() throws Exception{

    }

}