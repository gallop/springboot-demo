package com.gallop.file.security.service;

import com.alibaba.fastjson.JSON;
import com.gallop.file.pojo.SysPermission;
import com.gallop.file.pojo.SysUser;
import com.gallop.file.security.bean.SysLoginUser;
import com.gallop.file.service.PermissionService;
import com.gallop.file.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author gallop
 * date 2021-08-24 15:21
 * Description:
 * Modified By:
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getByUsername(username);
        if (null == sysUser) {
            log.warn("用户{}不存在", username);
            throw new UsernameNotFoundException(username);
        }
        List<SysPermission> permissionList = permissionService.findByUserId(sysUser.getUserId());
        HashSet<String> permissionSet = new HashSet<>(permissionList.stream().map(SysPermission::getPermission).collect(Collectors.toList()));

        //List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        SysLoginUser sysLoginUser = new SysLoginUser(sysUser,permissionSet);
        log.info("请求认证用户: {}", JSON.toJSONString(sysUser));
        return sysLoginUser;
    }
}
