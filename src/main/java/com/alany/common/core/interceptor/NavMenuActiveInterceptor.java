package com.alany.common.core.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class NavMenuActiveInterceptor extends HandlerInterceptorAdapter {

    @Value("${url.assets}")
    private String assetsUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getServletPath();
        request.setAttribute("currentMenu", url);
        request.setAttribute("assetsUrl", assetsUrl);
        return super.preHandle(request, response, handler);
    }

}