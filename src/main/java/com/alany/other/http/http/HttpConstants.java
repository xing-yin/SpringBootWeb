package com.alany.other.http.http;

public class HttpConstants {
    public static final int CLIENT_MAX_TOTAL = 100;

    public static final int CLIENT_DEFAULT_MAX_PER_ROUTE = 20;

    public static HttpClientConfig DEFAULT = new HttpClientConfig();

    public static HttpClientConfig SKONE = new HttpClientConfig(1200000, 30000, 1200000);

}
