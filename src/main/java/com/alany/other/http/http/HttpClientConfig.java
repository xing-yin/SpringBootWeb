package com.alany.other.http.http;

public class HttpClientConfig {


    private int connectTimeout = 60000;
    private int connectionRequestTimeout = 30000;
    private int socketTimeout = 60000;

//    DEFAULT(60000, 30000, 60000),
//    SKONE(1200000, 30000, 1200000);

    public HttpClientConfig() {

    }

    public HttpClientConfig(int connectTimeout,
                            int connectionRequestTimeout,
                            int socketTimeout) {
        this.connectTimeout = connectTimeout;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.socketTimeout = socketTimeout;
    }


    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
