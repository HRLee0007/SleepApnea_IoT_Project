package com.service.sleepapneaiotserver.web.service;

import com.service.sleepapneaiotserver.domain.user.User;
import com.service.sleepapneaiotserver.domain.user.UserRepository;
import com.service.sleepapneaiotserver.web.dto.UpdateResponseDto;
import com.service.sleepapneaiotserver.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

// 암호화하고 유저를 db에 저장시킴.
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long join(UserDto dto){
        dto.setPassword(encoder.encode(dto.getPassword()));
        return userRepository.save(dto.toEntity()).getId();
    }



    @Transactional
    public void statusOn(String username) {
        userRepository.statusOnChange(1, username);
        System.out.println("1에입장완료");
    }



    @Transactional
    public void statusOff(String username) {
        userRepository.statusOnChange(0, username);
        System.out.println("0에입장완료");
    }

    @Transactional
    public List<String> 유저메세지전송정보(String username){
        Optional<User> user = userRepository.findByUsername(username);
        List<String> userInfo = new LinkedList<>();
        userInfo.add(user.get().getC_phoneNum());
        userInfo.add(user.get().getRealname());
        userInfo.add(user.get().getAddress());

        System.out.println(userInfo);
        return userInfo;
    }
    @Transactional
    public String 유저이름확인(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getRealname();
    }
    @Transactional
    public int 현황확인(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getStatus();
    }

    @Transactional
    public int 사인변경(int sign, String username) {
        userRepository.signChange(sign, username);
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getSign();
    }
    @Transactional
    public int 사인확인(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getSign();
    }
    @Transactional
    public void 측정시작시간(String username, String now) {
        userRepository.changeStartTime(now, username);
    }

    @Transactional
    public void 측정종료시간(String username, String now) {
        userRepository.changeEndTime(now, username);
    }

    @Transactional
    public String 측정시작확인(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getStatusStartAt();
    }
    @Transactional
    public String 측정종료확인(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getStatusEndAt();
    }
    @Transactional
    public UpdateResponseDto 업뎃정보받기(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        UpdateResponseDto updateResponseDto = UpdateResponseDto.builder()
                .username(user.get().getUsername())
                .realname(user.get().getRealname())
                .email(user.get().getEmail())
                .address(user.get().getAddress())
                .c_phoneNum(user.get().getC_phoneNum())
                .phoneNum(user.get().getPhoneNum())
                .build();

        return updateResponseDto;
    }

    @Transactional
    public int update(UpdateResponseDto updateResponseDto) {
        Optional<User> user = userRepository.findByUsername(updateResponseDto.getUsername());
        user.get().setEmail(updateResponseDto.getEmail());
        user.get().setAddress(updateResponseDto.getAddress());
        user.get().setPhoneNum(updateResponseDto.getPhoneNum());
        user.get().setC_phoneNum(updateResponseDto.getC_phoneNum());

        return 1;
    }
}
