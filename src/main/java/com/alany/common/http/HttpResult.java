package com.alany.common.http;

/**
 * Created by yinxing on 2018/7/31.
 */
public class HttpResult {
    private int code;
    private String body;
    private String message;

    public HttpResult() {
    }

    public HttpResult(int code, String body) {
        this.code = code;
        this.body = body;
    }

    public HttpResult(int code, String body, String message) {
        this.code = code;
        this.body = body;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
