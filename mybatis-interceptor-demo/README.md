**mybatis-plus 敏感字段加密存储的做法**

本demo 介绍如何在mybatis 中对敏感字段的加密处理，主要有两种方式，一种是基于Mybatis的BaseTypeHandler通用处理类，另一种是实现mybatis 拦截器**Interceptor**接口，现在分别做介绍。

## 1、BaseTypeHandler 方式

### 1.1 创建基于Mybatis的BaseTypeHandler通用处理类：

```java
package com.gallop.mybatis.interceptor.mybatis;

import com.gallop.mybatis.interceptor.utils.SM4Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author gallop
 * date 2022-07-18 14:28
 * Description:
 * Modified By:
 */
@Slf4j
public class DataEncryptTypeHandler extends BaseTypeHandler<String> {
    /**
     * 非空字段加密 - 入库
     * @param preparedStatement
     * @param i
     * @param parameter
     * @param jdbcType
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String parameter, JdbcType jdbcType) {
        //不处理空字符串
        if(StringUtils.isBlank(parameter)){
            return;
        }
        try {
            SM4Utils sm4 = new SM4Utils();
            String encrypt = sm4.encryptData_ECB(parameter);
            log.info("数据：{},加密{}",parameter,encrypt);
            preparedStatement.setString(i, encrypt);
        } catch (Exception e) {
            log.error("typeHandler加密异常：" + e);
        }
    }
    /**
     * 非空字段解密 - 出库
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String col = resultSet.getString(columnName);
        //不处理空字符串
        if(StringUtils.isBlank(col)){
            return col;
        }
        try {
            //16位key
            SM4Utils sm4 = new SM4Utils();
            String plain = sm4.decryptData_ECB(col);
            log.info("数据：{},解密{}",col,plain);
            return plain;
        } catch (Exception e) {
            log.error("数据非sms加密");
        }
        return col;
    }

    /**
     * 可空字段加密
     * @param resultSet
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getString(columnIndex);
    }

    /**
     * 可空字段解密
     * @param callableStatement
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return callableStatement.getString(columnIndex);
    }


}

```



### 1.2 针对于Mybatis-plus用法
需要在@TableName注解中，设置autoResultMap参数为true
在需要加解密的字段上，加上注解 @TableField(typeHandler = SensitiveDataTypeHandler.class)
应用范围：所有MP生成的service与mapper，在进行操作数据的时候会自动应用

```java
package com.gallop.mybatis.interceptor.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gallop.mybatis.interceptor.mybatis.DataEncryptTypeHandler;
import com.gallop.mybatis.interceptor.mybatis.JsonIntegerArrayTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * author gallop
 * date 2022-07-17 14:26
 * Description:
 * // @Accessors(fluent = true) 与chain=true类似，区别在于getter和setter不带set和get前缀。
 * Modified By:
 */
@Data
//@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true) //链式访问
@TableName(value = "admin_user",autoResultMap = true)
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1860599553078982018L;

    /**
     * 用户id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 手机
     */
    @TableField(typeHandler = DataEncryptTypeHandler.class)
    private String phone;


}

```

### 1.3.针对于Mybatis的用法

SQL注解方式，配置 Result 示例：

```java
@Select("SELECT * FROM test_log")
 @Results(id= "resultMap", value = {
       @Result(column = "mobile", property = "mobile", typeHandler = TestDataTypeHandler .class)
 })
 List<TestLog> listAll();
```

XML方式，配置 typeHandler 示例：

```xml
<resultMap id="sysUserResult" type="com.gallop.mybatis.interceptor.pojo.SysUser">
        <result column="id" jdbcType="INTEGER" property="id" />
        <result column="phone" jdbcType="VARCHAR" property="phone" typeHandler="com.gallop.mybatis.interceptor.mybatis.DataEncryptTypeHandler" />
    </resultMap>
```



> 注意：加密字段不支持模糊查询。



## 2 mybatis 拦截器方式

**自定义一个mybatis的拦截器步骤包含：**

> - 定义一个实现org.apache.ibatis.plugin.Interceptor接口的拦截器类，并实现其中的方法。
> - 添加@Intercepts注解，写上需要拦截的对象和方法，以及方法参数。
> - 在mybatis的全局配置xml中配置插件plugin；对于去xml的Spring工程，显示的注册这个拦截器Bean即可；



### 2.1 添加注解
MyBatis拦截器默认可以拦截的类型只有四种，即四种接口类型Executor、StatementHandler、ParameterHandler和ResultSetHandler。对于我们的自定义拦截器必须使用MyBatis提供的@Intercepts注解来指明我们要拦截的是四种类型中的哪一种接口。

2.1.1 @Signature注解

```java
@Intercepts	// 描述：标志该类是一个拦截器
@Signature 	// 描述：指明该拦截器需要拦截哪一个接口的哪一个方法
```

2.1.2 @Signature注解中的type属性

```java
type; // 四种类型接口中的某一个接口，如Executor.class；
method; // 对应接口中的某一个方法名，比如Executor的query方法；
args; // 对应接口中的某一个方法的参数，比如Executor中query方法因为重载原因，有多个，args就是指明参数类型，从而确定是具体哪一个方法；
```

MyBatis拦截器默认会按顺序拦截以下的四个接口中的所有方法：

```javascript
org.apache.ibatis.executor.Executor  //拦截执行器方法
org.apache.ibatis.executor.statement.StatementHandler  //拦截SQL语法构建处理
org.apache.ibatis.executor.parameter.ParameterHandler  //拦截参数处理
org.apache.ibatis.executor.resultset.ResultSetHandler  //拦截结果集处理
```

实际上，具体是拦截这四个接口对应的实现类：

```
org.apache.ibatis.executor.CachingExecutor
org.apache.ibatis.executor.statement.RoutingStatementHandler
org.apache.ibatis.scripting.defaults.DefaultParameterHandler
org.apache.ibatis.executor.resultset.DefaultResultSetHandler
```



### 2.2 实现org.apache.ibatis.plugin.Interceptor接口
实现Interceptor接口，主要是实现下面几个方法：intercept(Invocation invocation)、plugin(Object target) 、setProperties(Properties properties)；

2.2.1 intercept方法

进行拦截的时候要执行的方法。该方法参数Invocation类中有三个字段：

```java
private final Object target;
private final Method method;
private final Object[] args;
```

可通过这三个字段分别获取下面的信息：

```java
Object target = invocation.getTarget(); //被代理对象
Method method = invocation.getMethod(); //代理方法
Object[] args = invocation.getArgs(); //方法参数
```

方法实现的结尾处，一般要真正调用被代理类的方法，即"return invocation.proceed();"

2.2.2 plugin方法

插件用于封装目标对象的，通过该方法我们可以返回目标对象本身，也可以返回一个它的代理，可以决定是否要进行拦截进而决定要返回一个什么样的目标对象，官方提供了示例："return Plugin.wrap(target, this);"；

Tips：可以在这个方法中提前进行拦截对象类型判断从而提高性能，如我们仅对Executor拦截，可以这么写：

    @Override
    public Object plugin(Object target) {
        // 只对要拦截制定类型的对象生成代理
        if(target instanceof Executor){
            // 调用插件
            return Plugin.wrap(target, this);
        }
        return target;
    }
MyBatis拦截器用到责任链模式+动态代理+反射机制；所有可能被拦截的处理类都会生成一个代理类，如果有N个拦截器，就会有N个代理，层层生成动态代理是比较耗性能的。而且虽然能指定插件拦截的位置，但这个是在执行方法时利用反射动态判断的，初始化的时候就是简单的把拦截器插入到了所有可以拦截的地方。所以尽量不要编写不必要的拦截器。另外我们可以在调用插件的地方添加判断，只要是当前拦截器拦截的对象才进行调用，否则直接返回目标对象本身，这样可以减少反射判断的次数，提高性能。

2.2.3 setProperties方法

如果我们拦截器需要用到一些变量参数，而且这个参数是支持可配置的，类似Spring中的@Value("${}")从application.properties文件获取自定义变量属性，这个时候我们就可以使用这个方法。

参考下方代码，在Mybatis配置文件中配置插件时可以设置参数，在setProperties函数中调用 Properties.getProperty("param1") 方法可以得到配置的值：

```
<plugins>
    <plugin interceptor="com.xx.xx.xxxInterceptor">
        <property name="param1" value="value1"/>
    </plugin>
</plugins>
```

实际上是解析XML，将XML中注册的拦截器的配置带过来；

### 2.3 代码示例

2.3.1 编写实现 Interceptor 接口的MybatisInterceptor类

```java
package com.gallop.mybatis.interceptor.mybatis;


import com.gallop.mybatis.interceptor.utils.BeanUtils;
import com.gallop.mybatis.interceptor.utils.MybatisInterceptorConfig;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class MybatisInterceptor implements Interceptor {

    private List<MybatisInterceptorConfig> interceptorConfigList;

    private ConcurrentHashMap<String, Class> classMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Map<String, Map<String, MybatisInterceptorConfig>>> interceptorConfigMap = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        Object parameter = invocation.getArgs()[1];
        if (parameter != null && methodName.equals("update")) {
            invocation.getArgs()[1] = process(parameter);
        }
        // do something ...方法拦截前执行代码块
        Object returnValue = invocation.proceed();
        
        // do something ...方法拦截后执行代码块
        
        Object result = returnValue;
        return result;
    }


    @Override
    public Object plugin(Object target) {
        // 只对要拦截制定类型的对象生成代理
        if(target instanceof Executor){
            // 调用插件
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub

    }


}

```



2.3.2 编写MybatisConfig 配置文件

```java
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
        configList.add(new MybatisInterceptorConfig(SysUser.class, "phone")); //这里值得需要加密的pojo类和字段
        interceptor.setInterceptorConfigList(configList);
        return interceptor;
    }
}

```

