

本demo主要介绍了spring security 框架的基本使用，并配置全部用json格式交互，以满足目前流行的前后端分离的开发。

主要的配置要点：

1、继承UsernamePasswordAuthenticationFilter 并重写相关方法以满足用户名、密码用json的形式进提交，登录验证；

2、配置登录验证成功、失败的handler，改成以json的形式返回；

3、配置登出成功的handler，改成以json的形式返回；

4、实现AccessDeniedHandler ，用户处理认证过的用户访问无权限资源时的异常处理；

5、实现AuthenticationEntryPoint，用户处理匿名用户访问需要权限的资源的异常处理；

6、实现UserDetailsService，以获取要登录的用户的数据库中相关的用户信息（包括相关的权限及角色的获取），这是登录验证前的必要步骤；

7、配置spring session redis缓存的开启，并可切换session id 是否存于cookies还是header中（在RedisSessionConfig.class类中配置）；

8、实现InvalidSessionStrategy，配置 session 无效时的处理策略；

9、实现SessionInformationExpiredStrategy，配置配置session过期处理策略；（配置允许同时登陆的最大session数。这里配置成 1，当用户在不同浏览器，或者不同机器下重复登陆后，系统会自动将前一次登录的 session 踢出，此时session 将会进入过期处理策略。）；

10、以上的配置大多需要在继续WebSecurityConfigurerAdapter 的配置类里进行相关的配置，详情查看WebSecurityConfig.class配置类；



spring security 的相关资料：

## 一、SpringSession 自带的 sessionId 存取器：

SpringSession中对于sessionId的存取相关的策略，是通过HttpSessionIdResolver这个接口来体现的。HttpSessionIdResolver有两个实现类：HeaderHttpSessionIdResolver 和 CookieHttpSessionIdResolver。

### 1、SpringSession header 存取 SessionID

HeaderHttpSessionIdResolver，通过从请求头header中解析出sessionId。

具体地说，这个实现将允许使用HeaderHttpSessionIdResolver(String)来指定头名称。还可以使用便利的工厂方法来创建使用公共头名称(例如“X-Auth-Token”和“authenticing-info”)的实例。创建会话时，HTTP响应将具有指定名称和sessionId值的响应头。

**如果要使用HeaderHttpSessionIdResolver ，方法为**：

增加Spring Bean，类型为 HeaderHttpSessionIdResolver



```java
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

//设置session失效时间为30分钟
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800)
public class HttpSessionConfig {

    @Bean
    public HeaderHttpSessionIdResolver headerHttpSessionIdResolver() {
        return new HeaderHttpSessionIdResolver("x-auth-token");
    }

}
```

sessionID放到header中可以实现共享了



### 2、 SpringSession cookie 存取 SessionID

这种策略对应的实现类是CookieHttpSessionIdResolver，通过从Cookie中获取session。

如果不做定制， SpringSession 默认的 IdResolver 就是这种了。

### 3、 SpringSession Attribute 存取 SessionID

如果要从 Attribute 存取 SessionID ,则必须实现一个定制的 HttpSessionIdResolver，代码如下：

````java
package com.crazymaker.springcloud.standard.config;

import com.crazymaker.springcloud.common.constants.SessionConstants;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@Data
public class CustomedSessionIdResolver implements HttpSessionIdResolver {

    private RedisTemplate<Object, Object> redisTemplet = null;

    private static final String HEADER_AUTHENTICATION_INFO = "Authentication-Info";

    private final String headerName;

    /**
     * Convenience factory to create {@link HeaderHttpSessionIdResolver} that uses
     * "X-Auth-Token" header.
     *
     * @return the instance configured to use "X-Auth-Token" header
     */
    public static HeaderHttpSessionIdResolver xAuthToken() {
        return new HeaderHttpSessionIdResolver(SessionConstants.SESSION_SEED);
    }

    /**
     * Convenience factory to create {@link HeaderHttpSessionIdResolver} that uses
     * "Authentication-Info" header.
     *
     * @return the instance configured to use "Authentication-Info" header
     */
    public static HeaderHttpSessionIdResolver authenticationInfo() {
        return new HeaderHttpSessionIdResolver(HEADER_AUTHENTICATION_INFO);
    }

    /**
     * The name of the header to obtain the session id from.
     *
     * @param headerName the name of the header to obtain the session id from.
     */
    public CustomedSessionIdResolver(String headerName) {
        if (headerName == null) {
            throw new IllegalArgumentException("headerName cannot be null");
        }
        this.headerName = headerName;
    }

    //重点，springsession 用来获得sessionID
    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        String headerValue = request.getHeader(this.headerName);
        if (StringUtils.isEmpty(headerValue)) {
            headerValue = (String) request.getAttribute(SessionConstants.SESSION_SEED);
            if (!StringUtils.isEmpty(headerValue)) {

                headerValue = SessionConstants.getRedisSessionID(headerValue);

            }
        }
        if (StringUtils.isEmpty(headerValue)) {
            headerValue = (String) request.getAttribute(SessionConstants.SESSION_ID);
        }

        return (headerValue != null) ?
                Collections.singletonList(headerValue) : Collections.emptyList();
    }

    //重点，springsession 用来存放sessionid
    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response,
                             String sessionId) {
        response.setHeader(this.headerName, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.headerName, "");
    }

    /**
     * hash的赋值去设置
     *
     * @param key   key
     * @param hkey  hkey
     * @param value value
     */
    public void hset(String key, String hkey, String value) {
        redisTemplet.opsForHash().put(key, hkey, value);
    }

    /**
     * hash的赋值去取值
     *
     * @param key  key
     * @param hkey hkey
     */
    public String hget(String key, String hkey) {
        return (String) redisTemplet.opsForHash().get(key, hkey);
    }

    public Object getSessionId(String loginName) {
        return hget(SessionConstants.SESSION_ID + ":KEYS", loginName);
    }

    public void setSessionId(String loginName, String sid) {
        hset(SessionConstants.SESSION_ID + ":KEYS", loginName, sid);
    }

}
````



## 二、spring security session管理

spring security 中提供了很好的 session 配置管理。包括session 无效处理、session 并发控制、session过期等相应处理配置。

　　在 Security 的配置中我们重写了 protected void configure(HttpSecurity http) 方法，在里面可以可以通过以下配置来达到以上描述的 Session 相关配置。

````java

http.sessionManagement()
// .invalidSessionUrl("http://localhost:8080/#/login")
　　.invalidSessionStrategy(invalidSessionStrategy)//session无效处理策略
　　.maximumSessions(1) //允许最大的session
// .maxSessionsPreventsLogin(true) //只允许一个地点登录，再次登陆报错
　　.expiredSessionStrategy(sessionInformationExpiredStrategy) //session过期处理策略，被顶号了
````

**invalidSessionUrl** ： 配置当 session 无效的时候的跳转地址，我们可以配置成当前系统的登录页即可。

**invalidSessionStrategy**：配置 session 无效时的处理策略，需要实现 InvalidSessionStrategy 接口，重写对应方法，默认为 SimpleRedirectInvalidSessionStrategy ，可以参考这个类来写我们的实现。这里就写的简单点如下：

```java
public class MyInvalidSessionStrategy implements InvalidSessionStrategy {

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String message = "session已失效";
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(message));
    }
}
```

**maximumSessions** ： 配置允许同时登陆的最大session数。这里配置成 1，当用户在不同浏览器，或者不同机器下重复登陆后，系统会自动将前一次登录的 session 踢出，此时session 将会进入过期处理策略。

**maxSessionsPreventsLogin** ： 当同时登陆的最大sseion 数达到 maximumSessions 配置后，拒绝后续同账户登录，抛出信息 ：Maximum sessions of 1 for this principal exceeded

**expiredSessionStrategy** ： session过期处理策略 ，需要实现 SessionInformationExpiredStrategy 接口，默认实现 SimpleRedirectSessionInformationExpiredStrategy。这里简单做一下实现：

```java
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    /* (non-Javadoc)
     * @see org.springframework.security.web.session.SessionInformationExpiredStrategy#onExpiredSessionDetected(org.springframework.security.web.session.SessionInformationExpiredEvent)
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        String message = "session过期处理。";

        event.getResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(JSON.toJSONString(message));
    }
}
```



## 三、 基于注解的权限控制

使用前提条件：

注解默认不可用，通过开启注解：在配置类中开启注解 @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)

- @Secured：专门判断用户是否具有角色，可以写在方法或类上，参数以 ROLE_ 开头
- @PreAuthorize\PostAuthorize： PreAuthorize 访问的类或方法执行前判断权限，而 PostAuthorize 在执行之后，Post 基本不用；允许与 ROLE_ 开头。



`@EnableGlobalMethodSecurity` 分别有`prePostEnabled` 、`securedEnabled`、`jsr250Enabled` 三个字段，其中每个字段代码一种注解支持，默认为false，true为开启。那么我们就一一来说一下这三总注解支持。

### **1. JSR-250注解**
主要注解

@DenyAll
@RolesAllowed
@PermitAll
这里面@DenyAll 和 @PermitAll 相信就不用多说了 代表拒绝和通过。

@RolesAllowed 使用示例

````java
@RolesAllowed({"USER", "ADMIN"})
````


代表标注的方法只要具有USER, ADMIN任意一种权限就可以访问。这里可以省略前缀ROLE_，实际的权限可能是ROLE_ADMIN。

### **2. securedEnabled注解**
主要注解

@Secured
@Secured在方法上指定安全性要求

可以使用@Secured在方法上指定安全性要求 角色/权限等 只有对应 角色/权限 的用户才可以调用这些方法。 如果有人试图调用一个方法，但是不拥有所需的 角色/权限，那会将会拒绝访问将引发异常。

@Secured使用示例

````java
@Secured("ROLE_ADMIN")
@Secured({ "ROLE_DBA", "ROLE_ADMIN" })
````


还有一点就是@Secured,不支持Spring EL表达式

### **3. prePostEnabled注解**
这个开启后支持Spring EL表达式 算是蛮厉害的。如果没有访问方法的权限，会抛出AccessDeniedException。

主要注解

@PreAuthorize --适合进入方法之前验证授权

@PostAuthorize --检查授权方法之后才被执行

@PostFilter --在方法执行之后执行，而且这里可以调用方法的返回值，然后对返回值进行过滤或处理或修改并返回

@PreFilter --在方法执行之前执行，而且这里可以调用方法的参数，然后对参数值进行过滤或处理或修改

@PreAuthorize 使用例子

在方法执行之前执行，而且这里可以调用方法的参数，也可以得到参数值，这里利用JAVA8的参数名反射特性，如果没有JAVA8，那么也可以利用Spring Secuirty的@P标注参数，或利用Spring Data的@Param标注参数。

````java
//无java8
@PreAuthorize("#userId == authentication.principal.userId or hasAuthority(‘ADMIN’)")
void changePassword(@P("userId") long userId ){}
//有java8
@PreAuthorize("#userId == authentication.principal.userId or hasAuthority(‘ADMIN’)")
void changePassword(long userId ){}
````


这里表示在changePassword方法执行之前，判断方法参数userId的值是否等于principal中保存的当前用户的userId，或者当前用户是否具有ROLE_ADMIN权限，两种符合其一，就可以访问该 方法。

### 4 常见内置表达式

| 表达                                                         | 描述                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| `hasRole([role])`                                            | 如果当前主体具有指定角色，则返回`true`。默认情况下，如果提供的角色不以“ROLE_”开头，则会添加该角色。这可以通过修改`DefaultWebSecurityExpressionHandler`上的`defaultRolePrefix`来自定义。 |
| `hasAnyRole([role1,role2])`                                  | 如果当前主体具有任何提供的角色（以逗号分隔的字符串列表给出），则返回`true`。默认情况下，如果提供的角色不以“ROLE_”开头，则会添加该角色。这可以通过修改`DefaultWebSecurityExpressionHandler`上的`defaultRolePrefix`来自定义。 |
| `hasAuthority([authority])`                                  | 如果当前主体具有指定的权限，则返回`true`。                   |
| `hasAnyAuthority([authority1,authority2])`                   | 如果当前主体具有任何提供的权限（以逗号分隔的字符串列表给出），则返回`true` |
| `principal`                                                  | 允许直接访问代表当前用户的主体对象                           |
| `authentication`                                             | 允许直接访问从`SecurityContext`获取的当前`Authentication`对象 |
| `permitAll`                                                  | 始终评估为`true`                                             |
| `denyAll`                                                    | 始终评估为`false`                                            |
| `isAnonymous()`                                              | 如果当前主体是匿名用户，则返回`true`                         |
| `isRememberMe()`                                             | 如果当前主体是remember-me用户，则返回`true`                  |
| `isAuthenticated()`                                          | 如果用户不是匿名用户，则返回`true`                           |
| `isFullyAuthenticated()`                                     | 如果用户不是匿名用户或记住我用户，则返回`true`               |
| `hasPermission(Object target, Object permission)`            | 如果用户有权访问给定权限的提供目标，则返回`true`。例如，`hasPermission(domainObject, 'read')` |
| `hasPermission(Object targetId, String targetType, Object permission)` | 如果用户有权访问给定权限的提供目标，则返回`true`。例如，`hasPermission(1, 'com.example.domain.Message', 'read')` |





### **自定义PermissionEvaluator**

