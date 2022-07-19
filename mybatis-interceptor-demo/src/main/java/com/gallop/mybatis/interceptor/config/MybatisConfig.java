package com.gallop.mybatis.interceptor.config;

import com.gallop.mybatis.interceptor.mybatis.MybatisInterceptor;
import com.gallop.mybatis.interceptor.pojo.SysUser;
import com.gallop.mybatis.interceptor.utils.MybatisInterceptorConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;


@Configuration
@MapperScan(basePackages = {"com.gallop.mybatis.interceptor.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class MybatisConfig {

    @Bean
    @ConditionalOnMissingBean
    public MybatisInterceptor dbInterceptor() {
        MybatisInterceptor interceptor = new MybatisInterceptor();
        List<MybatisInterceptorConfig> configList = new ArrayList<>();
        configList.add(new MybatisInterceptorConfig(SysUser.class, "phone"));
        interceptor.setInterceptorConfigList(configList);
        return interceptor;
    }
}
