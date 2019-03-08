package com.alany.other.http.http;

/**
 * Created by yinxing on 2018/7/31.
 */
public class HttpConstants {
    public static final int CLIENT_MAX_TOTAL = 100;
    public static final int CLIENT_DEFAULT_MAX_PER_ROUTE = 20;

    public HttpConstants() {
    }

    public static enum HttpClientConfig {
        DEFAULT('\uea60', 30000, '\uea60'),
        SKONE(1200000, 30000, 1200000);

        private int connectTimeout;
        private int connectionRequestTimeout;
        private int socketTimeout;

        private HttpClientConfig(int connectTimeout, int connectionRequestTimeout, int socketTimeout) {
            this.connectTimeout = connectTimeout;
            this.connectionRequestTimeout = connectionRequestTimeout;
            this.socketTimeout = socketTimeout;
        }

        public int getConnectTimeout() {
            return this.connectTimeout;
        }

        public int getConnectionRequestTimeout() {
            return this.connectionRequestTimeout;
        }

        public int getSocketTimeout() {
            return this.socketTimeout;
        }
    }
}
