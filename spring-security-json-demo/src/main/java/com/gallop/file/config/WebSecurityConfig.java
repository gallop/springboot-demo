package com.gallop.file.config;

import com.gallop.file.security.filter.LoginAuthenticationFilter;
import com.gallop.file.security.handler.*;
import com.gallop.file.security.service.MyUserDetailsService;
import com.gallop.file.security.strategy.MyExpiredSessionStrategy;
import com.gallop.file.security.strategy.MyInvalidSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * author gallop
 * date 2021-08-24 11:49
 * Description: 开启spring security的配置类
 * Modified By:
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private UrlAuthenticationSuccessHandler successHandler;
    @Autowired
    private UrlAuthenticationFailureHandler failureHandler;
    @Autowired
    private MyAccessDeineHandler myAccessDeineHandler;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    @Autowired
    private MyInvalidSessionStrategy invalidSessionStrategy;
    @Autowired
    private MyExpiredSessionStrategy expiredSessionStrategy;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        //super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**");
        //super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .antMatcher("/**").authorizeRequests()
            .antMatchers("/", "/login**").permitAll()
            .anyRequest().authenticated()
            //这里必须要写formLogin()，不然原有的UsernamePasswordAuthenticationFilter不会出现，也就无法配置我们重新的UsernamePasswordAuthenticationFilter
            .and().formLogin().loginProcessingUrl("/sysUser/login")//loginPage("/")
            .and().logout().logoutSuccessHandler(myLogoutSuccessHandler)
            .and().csrf().disable();

        //security session管理
        http.sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy) //session无效处理策略
                .maximumSessions(1) //允许最大的session(同一个账号只能登录一次)
                .expiredSessionStrategy(expiredSessionStrategy); //session过期处理策略，被顶号了
                // .maxSessionsPreventsLogin(true) //只允许一个地点登录，再次登陆报错

        //添加未授权处理
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        //权限不足处理
        http.exceptionHandling().accessDeniedHandler(myAccessDeineHandler);

        //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        http.addFilterAt(loginAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
        //super.configure(http);
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    protected LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(successHandler); //登录成功的handler
        filter.setAuthenticationFailureHandler(failureHandler); //登录失败的handler
        filter.setFilterProcessesUrl("/sysUser/login");//登录的访问路径

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }
}
