package com.alany.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author yinxing
 * @date 2019/3/14
 */

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 获取初始化参数
        String site = filterConfig.getInitParameter("site");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 将请求传回过滤链
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 在 filter 实例被 web 容器从服务器移除之前调用
    }
}
