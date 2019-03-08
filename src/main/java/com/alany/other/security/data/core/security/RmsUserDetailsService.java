package com.alany.other.security.data.core.security;

import com.alany.other.security.data.core.security.entity.SysUser;
import com.alany.other.security.data.core.security.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RmsUserDetailsService implements UserDetailsService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "rmsSysUserService")
    protected SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectByUserName(username);
        if (sysUser == null || StringUtils.isBlank(sysUser.getUsername())) {
            throw new UsernameNotFoundException("没有这个用户！");
        } else {
            logger.info("成功找到用户：" + sysUser.getUsername() + "," + sysUser.getEmail());
        }
        SecurityUser securityUser = new SecurityUser(sysUser.getId(),
                                                     sysUser.getUsername(),
                                                     sysUser.getPassword(),
                                                     sysUser.getEmail(),
                                                     sysUserService.getUserAuthorities(sysUser.getUsername()));
        return securityUser;
    }

}

