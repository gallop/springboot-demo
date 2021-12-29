package com.gallop.mongo.controller;

import com.sun.istack.internal.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author gallop
 * date 2021-08-25 13:52
 * Description:
 * Modified By:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/test")
    public Object read() {

        return "成功";
    }
}
