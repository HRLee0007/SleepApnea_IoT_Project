package com.service.sleepapneaiotserver.web.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.sleepapneaiotserver.domain.user.User;
import com.service.sleepapneaiotserver.web.dto.MessageDto;
import com.service.sleepapneaiotserver.web.dto.UpdateResponseDto;
import com.service.sleepapneaiotserver.web.dto.UserSessionDto;
import com.service.sleepapneaiotserver.web.service.SmsService;
import com.service.sleepapneaiotserver.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
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
    public int changeSign(@RequestParam("sign") int sign, @RequestParam("username") String username) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {

        if(sign == 3){
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
        return userService.사인변경(sign, username);
    }


}
