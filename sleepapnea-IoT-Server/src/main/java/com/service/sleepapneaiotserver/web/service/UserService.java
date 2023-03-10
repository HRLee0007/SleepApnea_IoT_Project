package com.service.sleepapneaiotserver.web.service;

import com.service.sleepapneaiotserver.domain.user.TokenRepository;
import com.service.sleepapneaiotserver.domain.user.User;
import com.service.sleepapneaiotserver.domain.user.UserRepository;
import com.service.sleepapneaiotserver.domain.user.UserToken;
import com.service.sleepapneaiotserver.web.dto.LoginDto;
import com.service.sleepapneaiotserver.web.dto.ResponseDto;
import com.service.sleepapneaiotserver.web.dto.UpdateResponseDto;
import com.service.sleepapneaiotserver.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public ResponseDto<UserDto> 로그인(LoginDto loginDto){

        // 요청성공 : 200, 요청실패 : 400
        //아이디에 해당되는 유저가 있는지 확인해라.
        Optional<User> user = userRepository.findByUsername(loginDto.getUsername());

        //해당되는 아이디가 없으면 (Optional 객체가 empty) 400, null객체 반환
        if(user.equals(Optional.empty())){
            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), new UserDto());
        }
        UserDto userDto = UserDto.builder()
                .username(user.get().getUsername())
                .password(user.get().getPassword())
                .address(user.get().getAddress())
                .email(user.get().getEmail())
                .c_phoneNum(user.get().getC_phoneNum())
                .phoneNum(user.get().getPhoneNum())
                .realname(user.get().getRealname())
                .role(user.get().getRole()).build();



        // 입력받은 pw, 데이터베이스 pw 가 일치하면 true,
        boolean checkEqu = encoder.matches(loginDto.getPassword(), user.get().getPassword());


        if(checkEqu == true){ // username, password 일치 하면

            statusReady(loginDto.getUsername()); // status = 2 로 변경 ( 아두이노에서 대기상태 )

            Optional<UserToken> userToken = tokenRepository.findByUsername(loginDto.getUsername());
            // username으로 UserToken 테이블에 기록 있는지 확인. ( 없으면 empty() )

            if(userToken.equals(Optional.empty())){

                // username에 맞는 레코드 없으면 Id 새로 받아서 저장.
                UserToken userToken1 = UserToken.builder()
                        .username(loginDto.getUsername())
                        .token(loginDto.getToken())
                        .build();
                tokenRepository.save(userToken1).getId();

            }
            else{
                // 해당 username이 DB에 이미 있으면 업데이트.

                tokenRepository.renewToken(loginDto.getToken(),loginDto.getUsername());
            }

            return new ResponseDto<>(HttpStatus.OK.value(), userDto);
            // username에 맞는 유저정보(userDto) 리턴
        }else{
            // 아디 비번 일치하지 않으면 에러 : 400, userDto -> NULL
            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), userDto);
        }
    }

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
    public void statusReady(String username) {
        userRepository.statusOnChange(2, username);
        System.out.println("2에입장완료");
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
    public String 토큰확인(String username) {
        Optional<UserToken> userToken = tokenRepository.findByUsername(username);
        return userToken.get().getToken();
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