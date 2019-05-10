package com.alany.servlet;

import org.apache.http.HttpRequest;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Servlet 实例
 *
 * @author yinxing
 * @date 2019/3/13
 */

public class HelloServlet extends HttpServlet {

    private String message;

    @Override
    public void init() throws ServletException {
        // 执行必须的初始化
        message = "hello servlet";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应内容类型
        resp.setContentType("text/html");

        // 实际的逻辑在这里编写
        PrintWriter out = resp.getWriter();
        out.println(message);


        // ======================================================
        /**
         * 有一个枚举，我们可以以标准方式循环枚举，使用 hasMoreElements() 方法来确定何时停止，使用 nextElement() 方法来获取每个参数的名称
         */
        // 读取所有可用的表单参数
        Enumeration paramNames = req.getParameterNames();
//        Enumeration paramNames2 = req.getHeaderNames();

        while (paramNames.hasMoreElements()) {
            String paraName = (String) paramNames.nextElement();
            System.out.println("paraName:" + paraName);
            String[] paramValues = req.getParameterValues(paraName);
            // 读取单个值
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() == 0) {
                    System.out.println("没有值");
                } else {
                    System.out.println("paraValue:" + paramValue);
                }
            } else {
                // 读取多个值
                for (int i = 0; i < paramValues.length; i++) {
                    System.out.println("paraValue:" + paramValues[i]);
                }
            }
        }
        // ======================================================
        // ======================================================
        /**
         * Servlet 客户端 HTTP 请求
         */
        Cookie[] cookies = req.getCookies();

        HttpSession session= req.getSession();
        // ======================================================
        /**
         * Servlet 服务器 HTTP 响应
         */
        String encodeURL = resp.encodeURL("http://www.test.com");
        // ======================================================

        /**
         * Servlet HTTP 状态码
         * 见 HttpServletResponse :  Server status codes; see RFC 2068.
         */
        resp.setStatus(200);

        resp.sendRedirect("/login");

        resp.sendError(403,"forbidden");

    }

    @Override
    public void destroy() {
        // nothing to do
    }

    public static void main(String[] args) {
    }
}
