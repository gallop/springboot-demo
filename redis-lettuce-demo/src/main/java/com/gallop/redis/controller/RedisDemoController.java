package com.gallop.redis.controller;

import com.gallop.redis.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * author gallop
 * date 2021-06-04 9:32
 * Description:
 * Modified By:
 */
@Slf4j
@RestController
@RequestMapping("/redis_demo")
public class RedisDemoController {

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    //@RequestParam String name
    public User getUserInfo(@RequestBody String name){
        log.info("name:"+name);
        User user = User.builder()
                .name("zhangsan")
                .age(29)
                .createTime(new Date())
                .build();
        return user;
    }

}
