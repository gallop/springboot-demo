package com.gallop.mybatis.cache2.controller;

import com.gallop.mybatis.cache2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author gallop
 * date 2021-12-29 14:15
 * Description:
 * Modified By:
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserList")
    public Object getUserList(){

        return userService.getUserList(null);
    }
}
