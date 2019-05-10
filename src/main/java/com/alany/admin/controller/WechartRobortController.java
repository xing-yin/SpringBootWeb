package com.alany.admin.controller;

import com.alany.other.http.http.HttpClientUtil;
import com.alany.other.http.http.HttpConstants;

/**
 * @author yinxing
 * @date 2019/5/10
 */
public class WechartRobortController {

    public static void main(String[] args) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key="+"6b794feb-bd3a-4468-9be3-06b617379ec3";
        String bodyJson ="{\"markdown\": {\"content\": \"测试\"},\"msgtype\": \"markdown\"}";
        HttpClientUtil.doPostBody(url, bodyJson, HttpConstants.DEFAULT);
    }
}
