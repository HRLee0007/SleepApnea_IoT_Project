//package com.service.sleepapneaiotserver.domain.infos;
//
//import com.service.sleepapneaiotserver.domain.user.User;
//import com.service.sleepapneaiotserver.domain.user.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class InfosRepositoryTest {
//
//    @Autowired
//    InfosRepository infosRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
////    @After
////    public void cleanup(){
////        infosRepository.deleteAll();
////    }
//
//    @Test
//    public void 인포_저장후_불러오기(){
//        //given
//        int pulse = 100;
//        double o2 = 99.8;
//        userRepository.save(User.builder()
//                        .username("alstjr")
//                        .password("111111")
//                        .realname("홍길동")
//                        .email("tjalstjr111@naver.com")
//                        .address("대구광역시 북구 경진로4길")
//                        .phoneNum("010-9999-9999")
//                        .c_phoneNum("010-9999-9991")
//                .build());
//        Optional<User> user = userRepository.findById(0L);
//
//
//
//        System.out.println(user.get().getUsername() + " " + user.get().getEmail());
//        infosRepository.save(Infos.builder()
//                .pulse(pulse)
//                .o2(o2)
//                .user(user.get())
//                .build());
//
//        infosRepository.save(Infos.builder()
//                .pulse(200)
//                .o2(99.9)
//                .user(user.get())
//                .build());
//
//        infosRepository.save(Infos.builder()
//                .pulse(300)
//                .o2(99.9)
//                .user(user.get())
//                .build());
//
//
//        //when
//        List<Infos> infosLists = infosRepository.findAll();
//
//        //then
//        Infos infos = infosLists.get(0);
//        Assertions.assertThat(infos.getPulse()).isEqualTo(pulse);
//        Assertions.assertThat(infos.getO2()).isEqualTo(o2);
//
//    }
//}