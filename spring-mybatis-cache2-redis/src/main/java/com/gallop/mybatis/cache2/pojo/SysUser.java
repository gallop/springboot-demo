package com.gallop.mybatis.cache2.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * author gallop
 * date 2021-08-24 14:26
 * Description:
 * // @Accessors(fluent = true) 与chain=true类似，区别在于getter和setter不带set和get前缀。
 * Modified By:
 */
@Data
//@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true) //链式访问
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "admin_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1860599553078982018L;

    /**
     * 用户id
     */
    @Id
    private Long id;


    /**
     * 用户名，登录名【不能为中文】
     */
    @Column(name = "username")
    private String username;

    /**
     * 用户密码
     */
    @Column(name = "password")
    @JsonIgnore
    private String password;

    /**
     * 生日 insertStrategy = FieldStrategy.IGNORED
     */
    @Column(name = "birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;

    /**
     * 性别[0:女;1:男]
     */
    @Column(name = "sex")
    private int sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称、姓名
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 手机
     */
    private String phone;

    /**
     * 最近一次登录IP地址
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 最近一次登录时间
     */
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;


    /**
     * 创建时间
     */
    @Column(name = "add_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime addTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    private Integer status;

    /**
     * 角色列表
     */
    @Column(name = "role_ids")
    private Integer[] roleIds;



    /**
     * 管理员类型（1超级管理员 0非管理员）
     */
    @Column(name = "admin_type")
    private Integer adminType;

    /**
     * 是否为管理员
     */
    @Ignore
    public boolean isAdmin() {
        return this.adminType==1;
    }

}
