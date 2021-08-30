package com.gallop.file.service.impl;

import com.gallop.file.pojo.SysUser;
import com.gallop.file.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * author gallop
 * date 2021-06-24 16:16
 * Description:
 * Modified By:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser getByUsername(String username) {
        SysUser user = new SysUser();
        user.setUserName("gallop");
        //$2a$10$P22hjdX4kNPkbn6wKfujhuvBdgkPGenmpdOOAgk4yBRG5juLG9wDG
        user.setPassword(passwordEncoder.encode("123456"));
        System.err.println("database-pwd:"+user.getPassword());
        return user;
    }
}
