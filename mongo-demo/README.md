# redis-cacheable-demo
本demo的主要内容如下：

一、介绍mongoDB下存储树形结构的目录文档数据



相关资料：

### 1.1、MongoDB简介 

MongoDB是一个基于分布式文件存储的数据库。由C++语言编写。旨在为WEB应用提供可扩展的高性能数据存储 解决方案。 MongoDB是一个介于关系数据库和非关系数据库之间的产品，是非关系数据库当中功能最丰富，最像关系数据库 的，它支持的数据结构非常松散，是类似json的bson格式，因此可以存储比较复杂的数据类型。 MongoDB最大的特点是它支持的查询语言非常强大，其语法有点类似于面向对象的查询语言，几乎可以实现类似 关系数据库单表查询的绝大部分功能，而且还支持对数据建立索引。 

官网：https://www.mongodb.com



### 1.2 MongoDB基本操作

为了更好的理解，下面与SQL中的概念进行对比：

| SQL术语/概念 | MongoDB术语/概念 | 解释/说明                           |
| ------------ | ---------------- | ----------------------------------- |
| database     | database         | 数据库                              |
| table        | collection       | 数据库表/集合                       |
| row          | document         | 数据记录行/文档                     |
| column       | field            | 数据字段/域                         |
| index        | index            | 索引                                |
| table joins  |                  | 表连接,MongoDB不支持                |
| primary key  | primary key      | 主键,MongoDB自动将_id字段设置为主键 |

