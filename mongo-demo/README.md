# redis-cacheable-demo
本demo的主要内容如下：

一、介绍mongoDB下存储树形结构的目录文档数据

数据库数据：

````
{
    "_id" : ObjectId("61231c4b559d6043d620189e"),
    "isFolder" : true,
    "type" : "folder",
    "value" : "it培训课程",
    "size" : NumberLong(0),
    "readCount" : 0,
    "downCount" : 0,
    "open" : true,
    "date" : ISODate("2021-08-23T11:55:55.827+08:00"),
    "_class" : "com.gallop.mongo.pojo.FileTreeNode"
}
{
    "_id" : ObjectId("612335aaf6ded90fdcad9dad"),
    "isFolder" : true,
    "type" : "folder",
    "value" : "java课程",
    "size" : NumberLong(0),
    "readCount" : 0,
    "downCount" : 0,
    "open" : true,
    "date" : ISODate("2021-08-23T13:44:10.522+08:00"),
    "parentId" : ObjectId("61231c4b559d6043d620189e"),
    "_class" : "com.gallop.mongo.pojo.FileTreeNode"
}
{
    "_id" : ObjectId("61233fd7af81f3473aa928b7"),
    "isFolder" : true,
    "ownId" : "ab001",
    "type" : "folder",
    "value" : "大数据课程",
    "size" : NumberLong(0),
    "readCount" : 0,
    "downCount" : 0,
    "open" : true,
    "date" : ISODate("2021-08-23T14:27:35.398+08:00"),
    "parentId" : ObjectId("61231c4b559d6043d620189e"),
    "_class" : "com.gallop.mongo.pojo.FileTreeNode"
}
{
    "_id" : ObjectId("612340290728f9263153afd7"),
    "isFolder" : true,
    "ownId" : "ab001",
    "type" : "folder",
    "value" : "springboot 入门",
    "size" : NumberLong(0),
    "readCount" : 0,
    "downCount" : 0,
    "open" : true,
    "date" : ISODate("2021-08-23T14:28:57.574+08:00"),
    "parentId" : ObjectId("612335aaf6ded90fdcad9dad"),
    "_class" : "com.gallop.mongo.pojo.FileTreeNode"
}
{
    "_id" : ObjectId("6123406060be7e08b5c69320"),
    "isFolder" : true,
    "ownId" : "ab001",
    "type" : "folder",
    "value" : "springCloud 进阶",
    "size" : NumberLong(0),
    "readCount" : 0,
    "downCount" : 0,
    "open" : true,
    "date" : ISODate("2021-08-23T14:29:52.311+08:00"),
    "parentId" : ObjectId("612335aaf6ded90fdcad9dad"),
    "_class" : "com.gallop.mongo.pojo.FileTreeNode"
}
{
    "_id" : ObjectId("6123409d896824016a74a0be"),
    "isFolder" : true,
    "ownId" : "ab001",
    "type" : "folder",
    "value" : "spark streaming 进阶",
    "size" : NumberLong(0),
    "readCount" : 0,
    "downCount" : 0,
    "open" : true,
    "date" : ISODate("2021-08-23T14:30:53.017+08:00"),
    "parentId" : ObjectId("61233fd7af81f3473aa928b7"),
    "_class" : "com.gallop.mongo.pojo.FileTreeNode"
}

{
    "_id" : ObjectId("612340bbe2d0ca7071c0c9dc"),
    "isFolder" : true,
    "ownId" : "ab001",
    "type" : "folder",
    "value" : "flink 实战",
    "size" : NumberLong(0),
    "readCount" : 0,
    "downCount" : 0,
    "open" : true,
    "date" : ISODate("2021-08-23T14:31:23.818+08:00"),
    "parentId" : ObjectId("61233fd7af81f3473aa928b7"),
    "_class" : "com.gallop.mongo.pojo.FileTreeNode"
}
{
    "_id" : ObjectId("6123594965ef014f4b3f1284"),
    "isFolder" : true,
    "ownId" : "ab001",
    "type" : "folder",
    "value" : "高中数学",
    "size" : NumberLong(0),
    "readCount" : 0,
    "downCount" : 0,
    "open" : true,
    "date" : ISODate("2021-08-23T16:16:09.804+08:00"),
    "_class" : "com.gallop.mongo.pojo.FileTreeNode"
}
{
    "_id" : ObjectId("61235975295be12716a75041"),
    "isFolder" : true,
    "ownId" : "ab001",
    "type" : "folder",
    "value" : "高中几何",
    "size" : NumberLong(0),
    "readCount" : 0,
    "downCount" : 0,
    "open" : true,
    "date" : ISODate("2021-08-23T16:16:53.180+08:00"),
    "parentId" : ObjectId("6123594965ef014f4b3f1284"),
    "_class" : "com.gallop.mongo.pojo.FileTreeNode"
}
{
    "_id" : ObjectId("6123598903be8c7d632f8c16"),
    "isFolder" : true,
    "ownId" : "ab001",
    "type" : "folder",
    "value" : "高中代数",
    "size" : NumberLong(0),
    "readCount" : 0,
    "downCount" : 0,
    "open" : true,
    "date" : ISODate("2021-08-23T16:17:13.721+08:00"),
    "parentId" : ObjectId("6123594965ef014f4b3f1284"),
    "_class" : "com.gallop.mongo.pojo.FileTreeNode"
}
````





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

