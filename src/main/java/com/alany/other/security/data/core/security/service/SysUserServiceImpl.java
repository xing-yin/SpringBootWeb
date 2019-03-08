package com.alany.other.security.data.core.security.service;

import com.alany.other.http.http.HttpClientUtil;
import com.alany.other.http.http.HttpConstants;
import com.alany.other.http.http.HttpResult;
import com.alany.other.security.data.core.security.entity.SysUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("SysUserService")
public class SysUserServiceImpl implements SysUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${security.url.user}")
    private String getUserUrl;

    @Value("${security.url.roles}")
    private String getRolesUrl;

    @Value("${security.user.platform}")
    public int platform;

    @Override
    public SysUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        }
        return selectByUserName(username);
    }

    @Override
    public SysUser selectByUserName(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        // 根据 username 获得用户信息
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("platform", platform);

        SysUser sysUser = null;
        try {
            HttpResult result = HttpClientUtil.doPost(getUserUrl, params, HttpConstants.HttpClientConfig.DEFAULT);
            JSONObject resultJson = JSON.parseObject(result.getBody());
            if (resultJson.getBooleanValue("success")) {
                JSONObject data = resultJson.getJSONObject("data");
                sysUser = new SysUser();
                sysUser.setId(data.getLong("id"));
                sysUser.setUsername(data.getString("username"));
                sysUser.setPassword(data.getString("password"));
                sysUser.setEmail(data.getString("email"));
            } else {
                logger.error("获取失败：" + result.getBody());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return sysUser;
    }

    @Override
    public List<SimpleGrantedAuthority> getUserAuthorities(String username) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        try {
            // 根据 username 获得用户权限
            Map<String, Object> params = new HashMap<>();
            params.put("username", username);
            params.put("platform", platform);
            HttpResult result = HttpClientUtil.doPost(getRolesUrl, params, HttpConstants.HttpClientConfig.DEFAULT);
            JSONObject resultJson = JSON.parseObject(result.getBody());
            if (resultJson.getBooleanValue("success")) {
                JSONArray permissionArray = resultJson.getJSONArray("data");
                for (int i = 0; i < permissionArray.size(); i++) {
                    JSONObject permissionJson = permissionArray.getJSONObject(i);
                    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permissionJson.getString("name"));
                    authorities.add(grantedAuthority);
                }
            } else {
                logger.error("获取失败：" + result.getBody());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return authorities;
    }

}
