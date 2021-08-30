package com.gallop.file.security.service;

import cn.hutool.core.util.StrUtil;
import com.gallop.file.security.bean.SysLoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * author gallop
 * date 2021-08-25 17:20
 * Description: 自定义权限判断
 * Modified By:
 */
@Service("aps")
public class AuthorityPermissionService {
    public boolean hasPermission(String permissions) {
        if (StrUtil.isBlank(permissions)) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysLoginUser loginUser = (SysLoginUser)authentication.getPrincipal();

        Set<String> authorities = loginUser.getPermissions();
        for (String permission : permissions.split(",")) {
            if (StrUtil.isNotEmpty(permission) && hasPermissions(authorities, permission)) {
                return true;
            }
        }
        return false;
    }
    private boolean hasPermissions(Set<String> authorities, String permission) {
        return authorities.contains("*") || authorities.contains(permission.trim());
    }
}
