//package com.alany.servlet;
//
//import org.springframework.messaging.MessagingException;
//import sun.rmi.transport.Transport;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Properties;
//
//import java.io.*;
//import java.util.*;
//import javax.servlet.*;
//import javax.servlet.http.*;
//import javax.activation.*;
//
///**
// * @author yinxing
// * @date 2019/3/15
// */
//
//public class SendEmail extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // 收件人邮件
//        String to = "receive@gmail.com";
//
//        // 发件人邮件
//        String from = "sender@gmail.com";
//
//        // 假设从本地主机发送邮件
//        String host = "localhost";
//
//        // 获取系统的属性
//        Properties properties = System.getProperties();
//
//        // 设置邮件服务器
//        properties.setProperty("mail.smtp.host", host);
//
//        // 获取默认的 session 对象
//        Session session = Session.getDefaultInstance(properties);
//
//        try {
//            // 创建一个默认的 MimeMessage 对象
//            MimeMessage message = new MimeMessage(session);
//            // 设置 From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//            // 设置 To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO,
//                    new InternetAddress(to));
//            // 设置 Subject: header field
//            message.setSubject("This is the Subject Line!");
//            // 现在设置实际消息
//            message.setText("This is actual message");
//            // 发送消息
//            Transport.send(message);
//            String title = "发送电子邮件";
//            String res = "成功发送消息...";
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//    }
//}
