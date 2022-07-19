package com.gallop.mybatis.mybatisinterceptordemo;

import com.gallop.mybatis.interceptor.MybatisInterceptorDemoApplication;
import com.gallop.mybatis.interceptor.param.SysUserParam;
import com.gallop.mybatis.interceptor.pojo.SysUser;
import com.gallop.mybatis.interceptor.service.SysUserService;
import com.gallop.mybatis.interceptor.utils.BeanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * author gallop
 * date 2022-07-18 10:16
 * Description:
 * Modified By:
 */
@SpringBootTest(classes = MybatisInterceptorDemoApplication.class)
public class UserServiceTest {
    @Resource
    private SysUserService sysUserService;

    @Test
    public void getUserTest(){
        SysUser user = sysUserService.getByUsername("my_gallop");
        System.out.println("admin:"+user);
        SysUserParam userParam = new SysUserParam();
        /*BeanUtils.copyBean(userParam,user);
        System.out.println("userParam:"+userParam.toString());
        sysUserService.edit(userParam);*/
        System.out.println("更新用户完成！");
    }

    @Test
    public void addUserTest(){
        SysUserParam userParam = new SysUserParam();
        userParam.setId(110l);
        userParam.setUsername("my_gallop");
        userParam.setPassword("123456");
        userParam.setBirthday("2000-01-01");
        userParam.setSex(1);
        userParam.setEmail("10001@qq.com");
        userParam.setNickName("gallop00");
        userParam.setPhone("15959202888");
        sysUserService.add(userParam);
        System.out.println("新增用户成功！");
    }
}
