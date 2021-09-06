

本demo主要介绍了dubbo 结合nacos 注册中心的的基本配置，原以为很简单，配置一下10几分钟就能搞定，没成想竟花了1天才找对依赖的版本配置，主要是dubbo nacos 都有alibaba的版本、apache的版本以及spring 的版本，有点小混乱，网上介绍的文章也都基本跑不起来，就连官网上的文章，按步骤配置，也跑不成功，目前这个demo的配置是确认可行的。

主要的配置要点：

1、nacos 的服务端安装的是nacos-2.0.3

2、dubbo 和 nacos-discovery 的依赖包都来自com.alibaba.cloud-2.2.1.RELEASE，其中dubbo依赖于org.apache.dubbo:bubbo:2.7.6

3、nacos-discovery 2.2.1.RELEASE 版本必须剔除 nacos-client （1.4.1的版本）依赖，并独立配置nacos-client:1.3.2的版本;

4、com.alibaba.cloud-2.2.1.RELEASE 的编译依赖于org.springframework.boot:2.3.2.RELEASE ,所以spring boot 的版本也要配置对才行；

5、org.apache.dubbo:bubbo:2.7.8 起的@Service 改成@DubboService，@Reference 改成@DubboReference，避免和spring boot的相同名字的注解混淆；

6、springboot 对bootstrap.yml 的读取的是优先于application.yml的，所以当启用配置中心的时候，项目的配置文件最好用bootstrap.yml 命名；

