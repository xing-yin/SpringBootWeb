package com.alany.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class HttpApiService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;


    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doGet(String url) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        HttpEntity entity = response.getEntity();

        HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(entity, "UTF-8"));

        // 释放资源
        EntityUtils.consume(response.getEntity());
        httpGet.releaseConnection();

        return result;
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doGet(String url, Map<String, Object> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());

    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        if (url.startsWith("https")) {
            httpClient = HttpClients.custom()
                    .setSSLSocketFactory(
                            new SSLConnectionSocketFactory(SSLContexts.custom()
                                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                                    .build())
                    ).build();
        }
        CloseableHttpResponse response = this.httpClient.execute(httpPost);

        HttpEntity entity = response.getEntity();

        HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(entity, "UTF-8"));

        // 释放资源
        EntityUtils.consume(response.getEntity());
        httpPost.releaseConnection();

        return result;
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url) throws Exception {
        return this.doPost(url, null);
    }

    /**
     * post body
     *
     * @param url
     * @param body
     * @return
     * @throws Exception
     */
    public HttpResult doPostBody(String url, String body) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);

        if (StringUtils.isNotBlank(body)) {
            StringEntity stringEntity = new StringEntity(body, "UTF-8");
            httpPost.setEntity(stringEntity);
        }

        // 发起请求
        if (url.startsWith("https")) {
            httpClient = HttpClients.custom()
                    .setSSLSocketFactory(
                            new SSLConnectionSocketFactory(SSLContexts.custom()
                                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                                    .build())
                    ).build();
        }
        CloseableHttpResponse response = this.httpClient.execute(httpPost);

        HttpEntity entity = response.getEntity();

        HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(entity, "UTF-8"));

        // 释放资源
        EntityUtils.consume(response.getEntity());
        httpPost.releaseConnection();

        return result;
    }

}