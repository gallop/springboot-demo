package com.gallop.dubbo.service;

import com.gallop.dubbo.pojo.User;

import java.util.List;

/**
 * author gallop
 * date 2021-05-24 21:28
 * Description:
 * Modified By:
 */
public interface UserService {
    /**
     * 查询所有的用户数据
     *
     * @return
     */
    List<User> queryAll();
}
