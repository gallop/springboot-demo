package com.gallop.file.security.bean;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.gallop.file.pojo.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * author gallop
 * date 2021-08-24 14:53
 * Description:
 * Modified By:
 */
@Data
public class SysLoginUser<T extends SysUser> implements UserDetails,Serializable {

    private static final long serialVersionUID = -1679277367393551347L;
    /**
     * 用户
     */
    private T user;

    /**
     * 权限标识集合
     */
    private Set<String> permissions;

    /**
     * 角色集合
     */
    private Set<String> roles;

    /**
     * 获取当前用户菜单集合
     */
    private Set<String> resources;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    public SysLoginUser(){
        super();
    }
    public SysLoginUser(T user,Set<String> permissions){
        //super(user.getUserName(), user.getPassword(),authorityList);
        this.user = user;
        this.permissions = permissions;
    }


    /**
     * 用户密码
     */
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> grantedAuthorities = CollectionUtil.newArrayList();
        if (ObjectUtil.isNotEmpty(roles)) {
            for (String role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }
    /**
     * 账户是否未过期，过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        //没有过期
        return true;
    }
    /**
     * 指定用户是否解锁，锁定的用户无法进行身份验证
     * <p>
     * 密码锁定
     * </p>
     */
    @Override
    public boolean isAccountNonLocked() {
        //return ObjectUtil.equal(this.user.getPwdLockStatus(), LockStatus.UN_LOCK);
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码)，过期的凭据防止认证
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否被启用或禁用。禁用的用户无法进行身份验证。
     */
    @Override
    public boolean isEnabled() {
        //return ObjectUtil.equal(this.user.getStopStatus(), StopStatus.ENABLE.getCode());
        return true;
    }
}
