package com.gallop.file.controller;

import com.gallop.file.base.BaseResultUtil;
import com.gallop.file.pojo.SysUser;
import com.sun.istack.internal.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/getUser")
    @PreAuthorize("@aps.hasPermission('admin:user:read')")
    public Object read(@NotNull Integer id) {
        SysUser user = new SysUser();
        user.setUserName("gallop");
        user.setEmail("123@qq.com");
        user.setPassword("123456");
        return BaseResultUtil.success(user);
    }
}
