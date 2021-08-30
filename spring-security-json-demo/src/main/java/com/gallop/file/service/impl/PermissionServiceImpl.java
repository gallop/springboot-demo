package com.gallop.file.service.impl;

import com.gallop.file.pojo.SysPermission;
import com.gallop.file.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author gallop
 * date 2021-08-24 15:43
 * Description:
 * Modified By:
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Override
    public List<SysPermission> findByUserId(String uid) {
        List<SysPermission> permissions = new ArrayList<>();
        SysPermission permi = new SysPermission();
        permi.setPermission("admin:user:read");
        permissions.add(permi);
        return permissions;
    }
}
