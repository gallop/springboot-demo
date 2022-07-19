package com.gallop.mybatis.interceptor.service;


import com.gallop.mybatis.interceptor.param.SysUserParam;
import com.gallop.mybatis.interceptor.pojo.SysUser;

import java.util.List;

/**
 * author gallop
 * date 2021-06-24 16:01
 * Description:
 * Modified By:
 */
public interface SysUserService {
    SysUser getByUsername(String username);
    List<SysUser> findAll();
    SysUser detail(SysUserParam sysUserParam);
    /**
     * 增加系统用户
     */
    void add(SysUserParam sysUserParam);
    /**
     * 编辑系统用户
     */
    void edit(SysUserParam sysUserParam);

}
