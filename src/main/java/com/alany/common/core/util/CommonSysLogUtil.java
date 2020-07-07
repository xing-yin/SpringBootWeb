package com.alany.common.core.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CommonSysLogUtil {

    private final static Logger sysLogger = LoggerFactory.getLogger("CommonSysLogUtil");

    public static void printApiRequestLog(String appId, String url, String params) {

        JSONObject infoJson = new JSONObject();
        infoJson.put("username", appId);
        infoJson.put("params", StringUtils.substring(params, 0, 1000));
        infoJson.put("url", url);
        sysLogInfo("api_request", infoJson);
    }

    public static void printApiResponseLog(String appId,
                                           String url,
                                           String params,
                                           Object result,
                                           Long responseTime) {
        String resultString = JSONObject.toJSONString(result);
        JSONObject infoJson = new JSONObject();
        infoJson.put("username", appId);
        infoJson.put("params", StringUtils.substring(params, 0, 1000));
        infoJson.put("result", StringUtils.substring(resultString, 0, 1000));
        try {
            JSONObject resultJson = JSONObject.parseObject(resultString);
            infoJson.put("message", resultJson.getString("message"));
            infoJson.put("queryCode", resultJson.getIntValue("code"));
        } catch (Exception e) {
        }
        infoJson.put("url", url);
        infoJson.put("responseTime", responseTime);
        sysLogInfo("api_response", infoJson);
    }

    public static void printErrorLog(String className, String function, String message) {
        JSONObject infoJson = new JSONObject();
        infoJson.put("className", className);
        infoJson.put("function", function);
        infoJson.put("message", message);
        sysLogError("error", infoJson);
    }

    public static String paramsSubstring(Map<String, String[]> paramsMap) {
        JSONObject paramsJson = new JSONObject();
        for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
            if(entry.getValue().length == 1) {
                paramsJson.put(entry.getKey(), StringUtils.substring(entry.getValue()[0], 0, 100));
            } else {
                paramsJson.put(entry.getKey(), StringUtils.substring(JSONObject.toJSONString(entry.getValue()), 0, 100));
            }
        }
        return paramsJson.toJSONString();
    }

    public static void sysLogInfo(String type, JSONObject infoJson) {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("info", infoJson);
        sysLogger.info(json.toJSONString());
    }

    public static void sysLogError(String type, JSONObject infoJson) {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("info", infoJson);
        sysLogger.error(json.toJSONString());
    }
}
