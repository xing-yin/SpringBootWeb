package com.alany.other.security.data.core.security.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUser implements Serializable {

    private Long id;

    private String username;

    private String email;

    private String password;

    /**
     * 所属平台：PlatformType
     */
    private Integer platform;

    /**
     * 1:有效，0:禁止登录
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Date lastLoginTime;

}
