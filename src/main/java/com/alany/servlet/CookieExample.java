package com.alany.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author yinxing
 * @date 2019/3/14
 */

public class CookieExample extends HttpServlet {

    public void encodeTest() {
        try {
            String str1 = URLEncoder.encode("中文", "UTF-8");
            String str2 = URLDecoder.decode("编码后的字符串", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setCookieByServlet(HttpServletResponse response) {
        Cookie cookie = new Cookie("name", "value");
        cookie.setMaxAge(60 * 60 * 24);
        // 发送 Cookie 到 http 响应头
        response.addCookie(cookie);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 为名字和姓氏创建 Cookie
        // 中文转码
        Cookie name = new Cookie("name", URLEncoder.encode(request.getParameter("name"), "UTF-8"));
        Cookie url = new Cookie("url", request.getParameter("url"));

        //  设置过期时间
        name.setMaxAge(60 * 60 * 24);
        url.setMaxAge(60 * 60 * 24);

        // 在响应头中添加两个 Cookie
        response.addCookie(name);
        response.addCookie(url);

        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");


        // ================================
        /**
         * 读取上面的实例中设置的 Cookie
         */
        Cookie cookie = null;
        Cookie[] cookies = null;
        // 获取该域名相关联到数组
        cookies = request.getCookies();

        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().compareTo("deleteCookie") == 0) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    System.out.println("已删除的cookie为:" + cookie);
                }

            }
        } else {
            System.out.println("没有找到cookie");
        }

        /**
         * 通过 Servlet 删除 Cookie
         删除 Cookie 是非常简单的。如果您想删除一个 cookie，那么您只需要按照以下三个步骤进行：ß
         1.读取一个现有的 cookie，并把它存储在 Cookie 对象中。
         2.使用 setMaxAge() 方法设置 cookie 的年龄为零，来删除现有的 cookie。
         3.把这个 cookie 添加到响应头。
         */

        /**
         * HttpSession 对象
         */
        HttpSession httpSession = request.getSession();
        httpSession.getAttribute("name");
        httpSession.getAttributeNames();
        httpSession.getId();
        httpSession.getMaxInactiveInterval();
        httpSession.invalidate();

        /**
         * Session 跟踪实例
         */
        // 如果不存在 session 会话，则创建一个 session 对象
        HttpSession session = request.getSession(true);
        // 获取 session 创建的时间
        Date createTime = new Date(session.getCreationTime());
        // 获取该网页的最后一次访问时间
        Date lastAccessTime = new Date(session.getLastAccessedTime());

        String title = "Servlet Session 实例";
        // 访问次数统计
        Integer visitCount = new Integer(0);
        String visitCountKey = new String("visitCount");
        String userIDKey = new String("userID");
        String userID = new String("userIDValue");
        if(session.getAttribute(visitCountKey)==null){
            session.setAttribute(visitCountKey,new Integer(0));
        }

        // 检查网页是否有新的访问者
        if(session.isNew()){
            title = "Servlet Session 实例";
            session.setAttribute(userIDKey,userID);
        }else {
            visitCount = (Integer) session.getAttribute(visitCountKey);
            visitCount = visitCount + 1;
            userID =(String) session.getAttribute(userIDKey);
        }
        session.setAttribute(visitCountKey,visitCount);




    }
}
