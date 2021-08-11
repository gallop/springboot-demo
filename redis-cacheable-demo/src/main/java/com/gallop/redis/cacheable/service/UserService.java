package com.gallop.redis.cacheable.service;

import com.gallop.redis.cacheable.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * author gallop
 * date 2021-08-10 11:08
 * Description:
 * Modified By:
 */
@Service
@Slf4j
public class UserService {

    @Cacheable(cacheNames = "user_test_name",key = "#name + '_' + #id")
    public List<User> getUserList(String id,String name){
        log.info("id:"+id);
        log.info("name:"+name);
        List<User> userList = new ArrayList<>();
        User user1 = User.builder()
                .id("0001")
                .name("test1")
                .age(19)
                .createTime(LocalDateTime.now()).build();

        User user2 = User.builder()
                .id("0002")
                .name("test2")
                .age(20)
                .createTime(LocalDateTime.now()).build();
        userList.add(user1);
        userList.add(user2);
        System.out.println("list:"+userList.toString());
        return userList;
    }
}
