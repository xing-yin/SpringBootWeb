package com.alany.common.core.controller.model;

import lombok.Getter;

@Getter
public class ResponseResults {

    private Boolean success = true;
    private int code = 200;
    private String message;
    private Object results;
    private Long responseTime = 0L;

    public ResponseResults() {
    }

    public static ResponseResults ok(Object results) {
        ResponseResults responseResults = new ResponseResults()
                .setSuccess(true)
                .setResults(results);
        return responseResults;
    }

    public static ResponseResults error(int code, String message) {
        ResponseResults responseResults = new ResponseResults()
                .setSuccess(false)
                .setCode(code)
                .setMessage(message);
        return responseResults;
    }

    public ResponseResults setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public ResponseResults setCode(int code) {
        this.code = code;
        return this;
    }

    public ResponseResults setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseResults setResults(Object results) {
        this.results = results;
        return this;
    }

    public ResponseResults setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
        return this;
    }
}

