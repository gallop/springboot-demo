package com.gallop.mybatis.interceptor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gallop.mybatis.interceptor.mapper.SysUserMapper;
import com.gallop.mybatis.interceptor.param.SysUserParam;
import com.gallop.mybatis.interceptor.pojo.SysUser;
import com.gallop.mybatis.interceptor.service.SysUserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * author gallop
 * date 2022-07-21 16:16
 * Description:
 * Modified By:
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        queryWrapper.eq(SysUser::getDeleted, 0);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<SysUser> findAll() {
        return this.list();
    }



    @Override
    public SysUser detail(SysUserParam sysUserParam) {
        SysUser sysUser = this.getById(sysUserParam.getId());
        if (ObjectUtil.isNull(sysUser)) {
            throw new RuntimeException("用户不存在");
        }
        return sysUser;
    }

    @Override
    public void add(SysUserParam sysUserParam) {
        checkAccountRepeat(sysUserParam,false); // 判断是否有重复账号
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserParam, sysUser);
        fillDefaltUserInfo(sysUser); //填充默认信息
        if(ObjectUtil.isNotEmpty(sysUserParam.getPassword())) {
            sysUser.setPassword(BCrypt.hashpw(sysUserParam.getPassword(), BCrypt.gensalt()));
        }else {
            //throw new ServiceException(SysUserExceptionEnum.USER_PWD_EMPTY);
            throw new RuntimeException("");
        }
        this.save(sysUser);
    }

    @Override
    public void edit(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        checkAccountRepeat(sysUserParam,true);
        BeanUtil.copyProperties(sysUserParam, sysUser);

        this.updateById(sysUser);
    }

    private SysUser querySysUser(SysUserParam sysUserParam) {
        SysUser sysUser = this.getById(sysUserParam.getId());
        System.err.println("sysUser====="+sysUserParam.getId());
        if (ObjectUtil.isNull(sysUser)) {
            throw new RuntimeException("出现异常！！");
        }
        return sysUser;
    }

     /**
      * @date 2021-11-20 8:00
      * Description:填充用户默认字段
      * Param:
      * return:
      **/
    private void fillDefaltUserInfo(SysUser sysUser) {

        if (ObjectUtil.isEmpty(sysUser.getAvatar())) {
            sysUser.setAvatar(null);
        }

        if (ObjectUtil.isEmpty(sysUser.getSex())) {
            sysUser.setSex(1);
        }
        sysUser.setAddTime(LocalDateTime.now());
        sysUser.setStatus(0);
        sysUser.setAdminType(0);
    }


    /**
     * 检查是否存在相同的账号
     *
     * @author xuyuxiang
     * @date 2020/3/27 16:04
     */
    private void checkAccountRepeat(SysUserParam sysUserParam, boolean isExcludeSelf) {
        Long id = sysUserParam.getId();
        String account = sysUserParam.getUsername();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, account)
                .ne(SysUser::getStatus, "0");
        //是否排除自己，如果是则查询条件排除自己id
        if (isExcludeSelf) {
            queryWrapper.ne(SysUser::getId, id);
        }
        int countByAccount = this.count(queryWrapper);
        //大于等于1个则表示重复
        if (countByAccount >= 1) {
            //throw new ServiceException(SysUserExceptionEnum.USER_ACCOUNT_REPEAT);
            throw new RuntimeException("用户帐户重复。");
        }
    }
}
