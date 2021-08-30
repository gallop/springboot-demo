package com.gallop.file.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysPermission {
    private Integer id;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 权限
     */
    private String permission;

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
}