package com.gallop.dubbo.service.impl;

import com.gallop.dubbo.pojo.User;
import com.gallop.dubbo.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;


import java.util.ArrayList;
import java.util.List;

/**
 * author gallop
 * date 2021-05-24 21:32
 * Description:
 * Modified By:
 */
@DubboService(version = "${user.service.version}") //声明这是一个dubbo服务
public class UserServiceImpl implements UserService {
    /**
     * 实现查询，这里做模拟实现，不做具体的数据库查询
     */
    @Override
    public List<User> queryAll() {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(10 + i);
            user.setId(Long.valueOf(i + 1));
            user.setPassword("123456");
            user.setUsername("username_" + i);
            list.add(user);
        }
        System.out.println("---------Service 3------------");
        return list;
    }
}
