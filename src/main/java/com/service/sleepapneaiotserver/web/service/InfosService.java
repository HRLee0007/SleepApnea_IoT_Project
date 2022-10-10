package com.service.sleepapneaiotserver.web.service;

import com.service.sleepapneaiotserver.domain.infos.Infos;
import com.service.sleepapneaiotserver.domain.infos.InfosRepository;
import com.service.sleepapneaiotserver.domain.user.User;
import com.service.sleepapneaiotserver.domain.user.UserRepository;
import com.service.sleepapneaiotserver.web.dto.InfosRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class InfosService {


    private final UserRepository userRepository;
    private final InfosRepository infosRepository;

    @Transactional
    public Long 저장하기(InfosRequestDto infosRequestDto) {
        int pulse = infosRequestDto.getPulse();
        double o2 = infosRequestDto.getO2();
        String username = infosRequestDto.getUsername();

        //username에 해당되는 해당 user객체를 찾자.
        Optional<User> user = userRepository.findByUsername(username);

        Infos infos = Infos.builder()
                        .pulse(pulse)
                        .o2(o2)
                        .user(user.get())
                        .build();

        return infosRepository.save(infos).getId();
    }

    @Transactional
    public List<Infos> 모두찾기(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return infosRepository.findAllByUserId(user.get().getId());
    }
}
