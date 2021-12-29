package com.gallop.mybatis.cache2.service.impl;

import com.gallop.mybatis.cache2.pojo.SysUser;
import com.gallop.mybatis.cache2.service.UserService;
import com.gallop.mybatis.cache2.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * author gallop
 * date 2021-12-29 13:39
 * Description:
 * Modified By:
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getUserList(Long userId) {
        return sysUserMapper.queryList(null);
    }
}
