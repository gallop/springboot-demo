package com.gallop.mybatis.cache2;

import com.gallop.mybatis.cache2.pojo.SysUser;
import com.gallop.mybatis.cache2.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = SpringMybatisCache2RedisApplication.class)
class SpringMybatisCache2RedisApplicationTests {
    @Autowired
    private UserService service;

    @Test
    void contextLoads() {

    }
    @Test
    public void userTest(){
        List<SysUser> userList = service.getUserList(null);
        System.err.println("userList="+userList.toString());
    }

}
