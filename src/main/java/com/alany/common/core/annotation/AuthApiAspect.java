package com.alany.common.core.annotation;

import com.alany.common.core.controller.model.ResponseResults;
import com.alany.other.http.http.HttpClientUtil;
import com.alany.other.http.http.HttpConstants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope
@Aspect
public class AuthApiAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${url.rms}")
    private String rmsUrl;
    @Value("${security.user.platform}")
    private int platform;

    @Autowired
    private HttpServletResponse response;

    @Pointcut("@annotation(com.alany.common.annotation.AuthApi)")
    public void serviceAspect() {
    }

    @Around("serviceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Object obj = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestUri = request.getRequestURI();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) joinPoint.getSignature();
        //返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //获取注解信息
        AuthApi annotation = currentMethod.getAnnotation(AuthApi.class);
        //将入参和值放入map
        Map<String, String[]> map = request.getParameterMap();

        //获取签名验证接口所需入参
        String[] emptyStringArray = new String[]{""};
        String appId = map.getOrDefault("appId", emptyStringArray)[0];
        String sign = map.getOrDefault("sign", emptyStringArray)[0];
        String timestamp = map.getOrDefault("timestamp", emptyStringArray)[0];
        String nonce = map.getOrDefault("nonce", emptyStringArray)[0];

//        if (StringUtils.isAnyBlank(appId, sign, timestamp, nonce)) {
//            outErrorResult(ResponseResults.error(0, "签名参数异常"));
//            log.info("签名参数异常！appId=" + appId + " ,sign= " + sign + " ,timestamp= " + timestamp + " ,nonce= " + nonce);
//            return obj;
//        }

        Map<String, String> authMap = new HashMap();
        authMap.put("timestamp", timestamp);
        authMap.put("nonce", nonce);
        String authFields = annotation.authFields();
        if (StringUtils.isNotBlank(authFields)) {
            String[] authFieldsSplit = annotation.authFields().split(",");
            for (String authField : authFieldsSplit) {
                authMap.put(authField.trim(), map.getOrDefault(authField.trim(), emptyStringArray)[0]);
            }
        }
        String checkResult = checkSignByAuthApi(appId, platform, authMap, sign, requestUri);
        try {
            if (checkResult.equals("")) {
                obj = joinPoint.proceed();
            } else {
                log.info("拒绝了请求：" + checkResult);
                outErrorResult(ResponseResults.error(0, checkResult));
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }

    /**
     * 返回空值代表成功，非空则是具体的错误原因
     */
    public String checkSignByAuthApi(String appId, int platform, Map authMap, String sign, String requestUri) {
        Map<String, Object> map = new HashMap<>();
        map.put("appId", appId);
        map.put("sign", sign);
        map.put("authJson", JSONObject.toJSONString(authMap));
        map.put("platform", platform);
        map.put("requestUri", requestUri);
        com.alany.other.http.http.HttpResult httpResult = HttpClientUtil.doPost(rmsUrl + "/api/auth/api", map, HttpConstants.DEFAULT);
        String result = "签名接口异常";
        if (httpResult.getCode() == 200) {
            JSONObject jsonObject = JSONObject.parseObject(httpResult.getBody());
            if (jsonObject != null) {
                if (jsonObject.getInteger("code") == 200) {
                    result = "";
                } else {
                    result = jsonObject.getString("message");
                }
            }
        }
        return result;
    }

    //将结果返回
    public void outErrorResult(Object result) {
        response.setContentType("application/json;charset=UTF-8");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(JSONObject.toJSONString(result).getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
