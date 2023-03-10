package com.service.sleepapneaiotserver.web.controller.api;

import com.service.sleepapneaiotserver.fcm.service.FCMService;
import com.service.sleepapneaiotserver.web.dto.*;
import com.service.sleepapneaiotserver.web.service.SmsService;
import com.service.sleepapneaiotserver.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final HttpSession httpSession;
    private final UserService userService;
    private final SmsService smsService;
    private final BCryptPasswordEncoder encoder;

    private final FCMService firebaseCloudMessageService;

//    @PostMapping("/api/v1/updateProc")
//    public int updateProfile(@RequestBody User user){
//        System.out.println("###################던져졌는가");
//        System.out.println(user.getUsername());
//        System.out.println(user.getRealname());
//        System.out.println(user.getC_phoneNum());
//        UpdateResponseDto updateResponseDto = UpdateResponseDto.builder()
//                .username(user.getUsername())
//                .realname(user.getRealname())
//                .email(user.getEmail())
//                .address(user.getAddress())
//                .phoneNum(user.getPhoneNum())
//                .c_phoneNum(user.getC_phoneNum())
//                .build();
//        return userService.update(updateResponseDto);
//    }

    // 1인지 0인지 요청하면 값 반환
    @GetMapping("/api/v1/user")
    public int findall(@RequestParam("username") String username){
        return userService.현황확인(username);
    }

    @GetMapping("/api/v1/userSign")
    public int changeSign(@RequestParam("sign") int sign, @RequestParam("username") String username) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException {

        //토큰을 먼저 불러와야함. mysql DB에서.

        if(sign == 0){ // 위험 0 : 정상 호흡
            String token = userService.토큰확인(username);
            List<String> userInfo = userService.유저메세지전송정보(username);
            String realName = userInfo.get(1);

            firebaseCloudMessageService.sendMessageTo(
                    token,
                    "위험 0 : 정상 호흡",
                    realName + "님의 호흡이 정상으로 회복되었습니다.");
        }

        if(sign == 1){ // 위험 1 : 진동
            String token = userService.토큰확인(username);
            List<String> userInfo = userService.유저메세지전송정보(username);
            String realName = userInfo.get(1);

            firebaseCloudMessageService.sendMessageTo(
                    token,
                    "위험 1 : 진동",
                    realName + "님의 수면무호흡 발생.\n5초 이상 호흡을 멈춘 상태입니다.");
        }
        //토큰을 먼저 불러와야함. mysql DB에서.
        if(sign == 2){ // 위험 2 : 소리
            String token = userService.토큰확인(username);
            List<String> userInfo = userService.유저메세지전송정보(username);
            String realName = userInfo.get(1);

            firebaseCloudMessageService.sendMessageTo(
                    token,
                    "위험 2 : 소리",
                    realName + "님의 수면무호흡 발생.\n8초 이상 호흡을 멈춘 상태입니다.");
        }

        if(sign == 3){ // 위험 3 : 보호자에게 긴급 문자 전송
            List<String> userInfo = userService.유저메세지전송정보(username);
            String phoneNum = userInfo.get(0);
            String realName = userInfo.get(1);
            String address = userInfo.get(2);

            MessageDto messageDto = MessageDto.builder()
                    .to(phoneNum)
                    .content(
                            realName + "님의 수면무호흡으로 인한 긴급 상황 발생." + "\n" +
                            "주소 : " + address + "\n" +
                            "빠른 조치 바람.")
                    .build();
            smsService.sendSms(messageDto);
        }

        if(sign == 4){ // sign 4 : 와이파이 연결 완료
            String token = userService.토큰확인(username);

            firebaseCloudMessageService.sendMessageTo2(token);
        }

        if(sign == 5){ // sign 5 : 초기측정 시작
            String token = userService.토큰확인(username);
            List<String> userInfo = userService.유저메세지전송정보(username);
            String realName = userInfo.get(1);

            firebaseCloudMessageService.sendMessageTo(
                    token,
                    "초기 측정 시작",
                    realName + "님의 호흡 패턴을 분석합니다.");
        }

        if(sign == 6){ // sign 6 : 초기측정 완료
            String token = userService.토큰확인(username);

            firebaseCloudMessageService.sendMessageTo3(token);
        }


        return 4;

    }
    // 로그인 처리 - 반환 유저 객체
    @PostMapping("/auth/loginProcAndroid")
    @ResponseBody
    public ResponseDto<UserDto> loginProcAndroid(@RequestBody LoginDto loginDto){
        //기존 유저인지 확인.
        ResponseDto<UserDto> checkedUserInfo = userService.로그인(loginDto);
        userService.statusReady(loginDto.getUsername());

       return checkedUserInfo;
    }
}