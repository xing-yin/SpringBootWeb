package com.alany.other.security.data.core.security.service;

import com.alany.other.security.data.core.security.entity.SysUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public interface SysUserService {

    /**
     * 获得当前登录用户
     */
    SysUser getLoginUser();

    /**
     * 根据用户名，获得用户基本信息
     *
     * @param username
     * @return
     */
    SysUser selectByUserName(String username);

    /**
     * 根据用户名，查询用户的权限
     *
     * @param username
     * @return
     */
    List<SimpleGrantedAuthority> getUserAuthorities(String username);

}
