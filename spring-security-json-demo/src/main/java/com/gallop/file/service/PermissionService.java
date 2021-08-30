package com.gallop.file.service;

import com.gallop.file.pojo.SysPermission;

import java.util.List;

/**
 * author gallop
 * date 2021-08-24 15:41
 * Description:
 * Modified By:
 */
public interface PermissionService {
    List<SysPermission> findByUserId(String uid);
}
