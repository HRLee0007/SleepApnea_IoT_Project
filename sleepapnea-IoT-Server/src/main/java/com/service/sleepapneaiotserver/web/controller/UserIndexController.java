package com.service.sleepapneaiotserver.web.controller;

import com.service.sleepapneaiotserver.web.dto.*;
import com.service.sleepapneaiotserver.web.service.InfosService;
import com.service.sleepapneaiotserver.web.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class UserIndexController {


    private final HttpSession httpSession;
    private final UserService userService;
    private final InfosService infosService;

    @GetMapping("/")
    public String mainPage(Model model){
       UserSessionDto user = (UserSessionDto) httpSession.getAttribute("user");
       model.addAttribute("username", user.getUsername());
       int status = userService.현황확인(user.getUsername());
       int sign = userService.사인확인(user.getUsername());

       //아두이노가 켜졋을때
       if(status == 1)
           model.addAttribute("status", status);

        model.addAttribute("sign", sign);
        return "index";
    }
    @GetMapping("/user/mypage")
    public String userMypage(Model model){
        UserSessionDto user = (UserSessionDto) httpSession.getAttribute("user");

        model.addAttribute("username", user.getUsername());
        model.addAttribute("infos", infosService.모두찾기(user.getUsername()));
        return "user/mypage";
    }

    //회원가입, 로그인통일
    @GetMapping("/auth/loginpage")
    public String login(){
        return "user/userjoin";
    }

    @PostMapping("/auth/joinProc")
    public String joinProc(UserDto userDto) {

        // 유저 가입완료.
        userService.join(userDto);
        return "redirect:/auth/loginpage";
//        return "1";
    }

    @PostMapping("/auth/joinProcAndroid")
    @ResponseBody
    public String joinProcAndroid(@RequestBody UserDto userDto2) {

        // 유저 가입완료.
        userService.join(userDto2);
        //return "redirect:/auth/loginpage";
        return "1";
    }

     //회원정보 수정
    @PostMapping("/auth/updateProc")
    public String joinProc(UpdateResponseDto updateResponseDto) {
        userService.update(updateResponseDto);
        return "redirect:/";

    }
    //로그아웃
    @GetMapping("/auth/logout")
    public String logout(){
        httpSession.invalidate();
        return "redirect:/auth/loginpage";
    }

    //사용자 데이터 수정
    @GetMapping("/user/updatePage")
    public String updatePage(Model model){
        UserSessionDto user = (UserSessionDto) httpSession.getAttribute("user");
        UpdateResponseDto updateResponseDto = userService.업뎃정보받기(user.getUsername());
        model.addAttribute("update", updateResponseDto);
        return "user/updatepage";
    }


    // status값 1로업데이트
    @GetMapping("/auth/statuson")
    public String statusOn(Model model){
        UserSessionDto user = (UserSessionDto) httpSession.getAttribute("user");
        userService.statusOn(user.getUsername());

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        String parsedLocalDateTimeNow = localDateTimeNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        userService.측정시작시간(user.getUsername(), parsedLocalDateTimeNow);

        model.addAttribute("username", user.getUsername());
        int status = userService.현황확인(user.getUsername());
        //아두이노가 켜졋을때
        if(status == 1)
            model.addAttribute("status", status);
        return "redirect:/";
    }

    @PostMapping("/auth/statusAndroid")
    @ResponseBody
    public Integer statusAndroid(@RequestBody MeasureRequestDto measureRequestDto) {

        if(measureRequestDto.getStatus() == 0) { //
            userService.statusOn(measureRequestDto.getUsername());

            // mysql DB에서
            // return (안드로이드에서 필요한 유저정보들);
            return 1;
        }
        else if(measureRequestDto.getStatus() == 1){
            userService.statusOff(measureRequestDto.getUsername());

            return 0;
        }
        else // status 값 오류 -> -1 반환
            return -1;
    }



    @GetMapping("/auth/statusoff")
    public String statusOff(Model model){
        UserSessionDto user = (UserSessionDto) httpSession.getAttribute("user");
        userService.statusOff(user.getUsername());

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        String parsedLocalDateTimeNow = localDateTimeNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        userService.측정종료시간(user.getUsername(), parsedLocalDateTimeNow);
        model.addAttribute("username", user.getUsername());
        int status = userService.현황확인(user.getUsername());
        //아두이노가 켜졋을때
        if(status == 1)
            model.addAttribute("status", status);
        return "redirect:/";
    }

}
