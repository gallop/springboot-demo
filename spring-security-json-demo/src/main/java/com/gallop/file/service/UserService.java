package com.gallop.file.service;


import com.gallop.file.pojo.SysUser;

/**
 * author gallop
 * date 2021-06-24 16:01
 * Description:
 * Modified By:
 */
public interface UserService {
    SysUser getByUsername(String username);
}
