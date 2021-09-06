package com.gallop.dubbo.nacos.provider.service;

//import com.alibaba.dubbo.config.annotation.Service;
//import com.alibaba.dubbo.config.annotation.Service;
import com.gallop.dubbo.pojo.User;
import com.gallop.dubbo.service.UserService;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Service;
//import org.apache.dubbo.config.annotation.Service;
//import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;

/**
 * author gallop
 * date 2021-09-03 11:19
 * Description:
 * Modified By:
 */
//声明这是一个dubbo服务 ${user.service.version}
//@Service(version = "1.0.0")
//@DubboReference(version = "1.0.0")
@Service(version = "1.0.0")
public class UserServiceImpl implements UserService {

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
        return list;
    }
}
