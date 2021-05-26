# springboot-dubbo-demo
springboot-dubbo 示例代码

官方github 地址：
https://github.com/apache/dubbo-spring-boot-project

官方samples github地址：
https://github.com/apache/dubbo-spring-boot-project/tree/master/dubbo-spring-boot-samples

## 项目结构说明

- dubbo-server-user-service： dubbo服务提供端项目
  - dubbo-server-user-interface： dubbo服务端接口定义
  - dubbo-server-user-service： dubbo服务端接口实现
- dubbo-consumer-user： dubbo服务消费端项目


## N/A 代表不在zookeeper上注册服务,服务端提供方和消费方直接本地连接,项目开发阶段直连比较方便
## Dubbo Registry
dubbo.registry.address=N/A

