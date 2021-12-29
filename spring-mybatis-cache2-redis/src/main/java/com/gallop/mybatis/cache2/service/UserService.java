package com.gallop.mybatis.cache2.service;

import com.gallop.mybatis.cache2.pojo.SysUser;

import java.util.List;

/**
 * author gallop
 * date 2021-12-29 13:38
 * Description:
 * Modified By:
 */
public interface UserService {
    List<SysUser> getUserList(Long userId);
}
