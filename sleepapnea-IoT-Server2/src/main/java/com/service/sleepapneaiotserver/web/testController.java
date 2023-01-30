package com.service.sleepapneaiotserver.web;

import com.service.sleepapneaiotserver.domain.testDom;
import com.service.sleepapneaiotserver.web.dto.TestRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class testController {

//    @RequestMapping(value = "/api/v1/test1", method = RequestMethod.POST, produces = {"application/json"})
//    public Map<String, Object> startTest1(@RequestBody Map<String, Object> info){
//        System.out.println(info.get("sensor1") + " " + info.get("sensor2"));
//        Map<String, Object> temp = new HashMap<>();
//        temp.put("sensor1", "9999");
//        temp.put("sensor2", "9999");
//        return temp;
//    }


    @RequestMapping(value = "/api/v1/test1", method = RequestMethod.POST, produces = {"application/json"})
    public String startTest1(@RequestBody Map<String, Object> info){
        System.out.println(info.get("time") + " " +  info.get("bpmCount") + " " +
                info.get("o2Count") + " " +  info.get("noisyCount"));
        return "success";
    }
    @GetMapping("/api/v1/test2")
    public String startTest2(@RequestParam("phone") String num,
                             @RequestParam("address") String address){

        String temp = num + " " + address;
        System.out.println(temp);
        return temp;
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @PostMapping("/hello/post")
    public String hello_post(@RequestBody TestRequestDto info){
        return info.getTime() + "is" + info.getBpmCount();
    }

}
