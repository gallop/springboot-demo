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
 * date 2021-08-24 14:26
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
     * 用户名，登录名【不能为中文】
     */
    @TableField("username")
    private String username;

    /**
     * 用户密码
     */
    @TableField("password")
    @JsonIgnore
    private String password;

    /**
     * 生日 insertStrategy = FieldStrategy.IGNORED
     */
    @TableField("birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;

    /**
     * 性别[0:女;1:男]
     */
    @TableField("sex")
    private int sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称、姓名
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 手机
     */
    //@TableField(typeHandler = DataEncryptTypeHandler.class)
    private String phone;

    /**
     * 最近一次登录IP地址
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 最近一次登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;


    /**
     * 创建时间
     */
    @TableField("add_time")
    private LocalDateTime addTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
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
    @TableField(value = "role_ids",typeHandler = JsonIntegerArrayTypeHandler.class)
    private Integer[] roleIds;



    /**
     * 管理员类型（1超级管理员 0非管理员）
     */
    @TableField("admin_type")
    private Integer adminType;

    /**
     * 是否为管理员
     */
    public boolean isAdmin() {
        return this.adminType==1;
    }

}
