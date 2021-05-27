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

  

## 1. dubbo 架构

![](.\image\image-20210526174242901.png)

> 节点角色说明

|   节点    |                角色说明                |
| :-------: | :------------------------------------: |
| Provider  |          暴露服务的服务提供方          |
| Consumer  |        调用远程服务的服务消费方        |
| Registry  |        服务注册与发现的注册中心        |
|  Monitor  | 统计服务的调用次数和调用时间的监控中心 |
| Container |              服务运行容器              |



> 调用关系说明

1. 服务容器负责启动，加载，运行服务提供者。
2. 服务提供者在启动时，向注册中心注册自己提供的服务。
3. 服务消费者在启动时，向注册中心订阅自己所需的服务。
4. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
5. 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另
一台调用。
6. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。

Dubbo 架构具有以下几个特点，分别是连通性、健壮性、伸缩性、以及向未来架构的升级性。
更多介绍参见：http://dubbo.apache.org/zh-cn/docs/user/preface/architecture.html

## 2. 注册中心

dubbo支持多种注册中心，推荐使用ZooKeeper。

> 使用docker部署ZooKeeper

````
#拉取zk镜像
docker pull zookeeper:3.6
#创建并启动容器
docker run --name myzk -p 2181:2181 -v /home/zookeeper/data:/data -v /home/zookeeper/log:/datalog -itd zookeeper:3.6

````



## 3. 服务的负载均衡

在集群负载均衡时，Dubbo 提供了多种均衡策略，缺省为 random 随机调用。

具体参看：https://dubbo.apache.org/zh/docs/v2.7/dev/source/loadbalance/#22-leastactiveloadbalance

### 1. Random LoadBalance(随机均衡算法)

- 随机，按权重设置随机概率。
- 在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。

### 2. RoundRobin LoadBalance(权重轮循均衡算法)

- 轮循，按公约后的权重设置轮循比率。
- 存在慢的提供者累积请求问题，比如：第二台机器很慢，但没挂，当请求调到第二台时就卡在那，久而久之，所有请求都卡在调到第二台上。(针对此种情况，需要降低该服务的权值，以减少对其调用)

### 3. LeastAction LoadBalance(最少活跃调用数均衡算法)

- 最少活跃调用数，相同活跃数的随机，活跃数指调用前后计数差。
- 使慢的提供者收到更少请求，因为越慢的提供者的调用前后计数差会越大。

### 4. ConsistentHash LoadBalance(一致性Hash均衡算法)

- 一致性Hash，相同参数的请求总是发到同一提供者。
- 当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动。





## N/A 代表不在zookeeper上注册服务,服务端提供方和消费方直接本地连接,项目开发阶段直连比较方便

## Dubbo Registry
dubbo.registry.address=N/A

