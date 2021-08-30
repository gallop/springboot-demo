package com.gallop.file.pojo;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gallop.file.enums.LockStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@TableName("system_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1860599553078982018L;

    /**
     * 用户id
     */
    @TableId(value = "user_id",type = IdType.ASSIGN_UUID)
    private String userId;

    /**
     * 所属机构代码
     */
    @TableField("org_id")
    private String orgId;

    /**
     * 用户名，登录名【不能为中文】
     */
    @TableField("user_name")
    private String userName;

    /**
     * 性别[0:女;1:男]
     */
    @TableField("sex")
    private String sex;

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
     * 用户密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 微信号
     */
    @TableField("wechat")
    private String wechat;

    /**
     * 密码修改时间
     */
    @TableField("pwd_update_time")
    private LocalDateTime pwdUpdateTime;

    /**
     * 启停状态[0:启用;1:停用]
     */
    @TableField("stop_status")
    private String stopStatus;

    /**
     * 停用时间
     */
    @TableField("stop_time")
    private LocalDateTime stopTime;

    /**
     * 停用原因
     */
    @TableField("stop_reason")
    private String stopReason;

    /**
     * 密码错误次数
     */
    @TableField("pwd_error_number")
    private BigDecimal pwdErrorNumber;

    /**
     * 密码锁定状态[0:未锁定;1:已锁定]
     */
    @TableField("pwd_lock_status")
    private LockStatus pwdLockStatus;

    /**
     * 用户锁定时间
     */
    @TableField("lock_time")
    private LocalDateTime lockTime;

    /**
     * 最近成功登陆时间
     */
    @TableField("login_success_time")
    private LocalDateTime loginSuccessTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否为管理员
     */
    public boolean isAdmin() {
        return StrUtil.equals(this.userId, "1");
    }
}
