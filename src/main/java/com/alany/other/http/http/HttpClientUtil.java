package com.alany.other.http.http;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by yinxing on 2018/7/31.
 */

public class HttpClientUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    private static CloseableHttpClient httpClient = null;
    private static final Object syncLock = new Object();

    public HttpClientUtil() {
    }

    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            Object var0 = syncLock;
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = createHttpClient();
                }
            }
        }

        return httpClient;
    }

    public static CloseableHttpClient createHttpClient() {
        ConnectionSocketFactory plainConnectionSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();

        try {
            sslConnectionSocketFactory = new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial((KeyStore) null, new TrustSelfSignedStrategy()).build());
        } catch (NoSuchAlgorithmException var7) {
            LOGGER.error(var7.getMessage(), var7);
        } catch (KeyManagementException var8) {
            LOGGER.error(var8.getMessage(), var8);
        } catch (KeyStoreException var9) {
            LOGGER.error(var9.getMessage(), var9);
        }

        RegistryBuilder registryBuilder = RegistryBuilder.create().register("http", plainConnectionSocketFactory).register("https", sslConnectionSocketFactory);
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        httpClientConnectionManager.setMaxTotal(100);
        httpClientConnectionManager.setDefaultMaxPerRoute(20);
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 5) {
                    return false;
                } else if (exception instanceof NoHttpResponseException) {
                    return true;
                } else if (exception instanceof SSLHandshakeException) {
                    return false;
                } else if (exception instanceof InterruptedIOException) {
                    return false;
                } else if (exception instanceof UnknownHostException) {
                    return false;
                } else if (exception instanceof ConnectTimeoutException) {
                    return false;
                } else if (exception instanceof SSLException) {
                    return false;
                } else {
                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    return !(request instanceof HttpEntityEnclosingRequest);
                }
            }
        };
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(httpClientConnectionManager).setRetryHandler(httpRequestRetryHandler).build();
        return httpClient;
    }

    private static void setRequestConfig(HttpRequestBase httpRequestBase, HttpConstants.HttpClientConfig httpClientConfig) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(httpClientConfig.getConnectTimeout()).setConnectionRequestTimeout(httpClientConfig.getConnectionRequestTimeout()).setSocketTimeout(httpClientConfig.getSocketTimeout()).build();
        httpRequestBase.setConfig(requestConfig);
    }

    public static HttpResult doGet(String url) {
        return doGet(url, (Map) null, HttpConstants.HttpClientConfig.DEFAULT);
    }

    public static HttpResult doGet(String url, HttpConstants.HttpClientConfig httpClientConfig) {
        return doGet(url, (Map) null, httpClientConfig);
    }

    public static HttpResult doGet(String url, Map<String, Object> params, HttpConstants.HttpClientConfig httpClientConfig) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null) {
                Iterator var4 = params.entrySet().iterator();

                while (var4.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry) var4.next();
                    uriBuilder.setParameter((String) entry.getKey(), entry.getValue().toString());
                }
            }

            url = uriBuilder.build().toString();
            HttpGet httpGet = new HttpGet(url);
            setRequestConfig(httpGet, httpClientConfig);
            CloseableHttpResponse response = getHttpClient().execute(httpGet);
            HttpEntity entity = response.getEntity();
            HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(entity, "UTF-8"));
            EntityUtils.consume(entity);
            httpGet.releaseConnection();
            return result;
        } catch (Exception var8) {
            LOGGER.error(var8.getMessage(), var8);
            return new HttpResult(0, "", var8.getMessage());
        }
    }

    public static HttpResult doPost(String url) {
        return doPost(url, (Map) null, HttpConstants.HttpClientConfig.DEFAULT);
    }

    public static HttpResult doPost(String url, HttpConstants.HttpClientConfig httpClientConfig) {
        return doPost(url, (Map) null, httpClientConfig);
    }

    public static HttpResult doPost(String url, Map<String, Object> params, HttpConstants.HttpClientConfig httpClientConfig) {
        HttpPost httpPost = new HttpPost(url);
        setRequestConfig(httpPost, httpClientConfig);
        setPostParams(httpPost, params);

        try {
            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            HttpEntity entity = response.getEntity();
            HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(entity, "UTF-8"));
            EntityUtils.consume(response.getEntity());
            httpPost.releaseConnection();
            return result;
        } catch (Exception var7) {
            LOGGER.error(var7.getMessage(), var7);
            return new HttpResult(0, "", var7.getMessage());
        }
    }

    public static HttpResult doPostBody(String url, String bodyJson, HttpConstants.HttpClientConfig httpClientConfig) {
        HttpPost httpPost = new HttpPost(url);
        setRequestConfig(httpPost, httpClientConfig);

        try {
            StringEntity entity = new StringEntity(bodyJson, "utf-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-type", "application/json");
        } catch (Exception var8) {
            LOGGER.error(var8.getMessage(), var8);
            return new HttpResult(0, "", var8.getMessage());
        }

        try {
            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            HttpEntity entity = response.getEntity();
            HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(entity, "UTF-8"));
            EntityUtils.consume(response.getEntity());
            httpPost.releaseConnection();
            return result;
        } catch (Exception var7) {
            LOGGER.error(var7.getMessage(), var7);
            return new HttpResult(0, "", var7.getMessage());
        }
    }

    private static void setPostParams(HttpPost httpPost, Map<String, Object> params) {
        if (params != null && params.size() > 0) {
            List<NameValuePair> list = new ArrayList();
            Iterator var3 = params.entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry) var3.next();
                list.add(new BasicNameValuePair((String) entry.getKey(), entry.getValue().toString()));
            }

            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
                httpPost.setEntity(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException var5) {
                LOGGER.error(var5.getMessage(), var5);
            }
        }

    }
}

