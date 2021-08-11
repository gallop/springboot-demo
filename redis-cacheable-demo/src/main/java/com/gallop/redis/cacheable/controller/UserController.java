package com.gallop.redis.cacheable.controller;

import com.gallop.redis.cacheable.pojo.User;
import com.gallop.redis.cacheable.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * author gallop
 * date 2021-08-10 11:04
 * Description:
 * Modified By:
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<User> getUserInfo(String id,String name){
        log.info("name:"+name);
        return userService.getUserList(id,name);
    }
}
