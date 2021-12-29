package com.gallop.mybatis.cache2.mapper;

import com.gallop.mybatis.cache2.pojo.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author gallop
 * date 2021-10-04 16:57
 * Description:
 * Modified By:
 */
@Repository
public interface SysUserMapper {
    List<SysUser> queryList(@Param("userId") Long userId);
}
