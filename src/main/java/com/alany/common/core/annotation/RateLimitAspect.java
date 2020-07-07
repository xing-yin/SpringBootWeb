package com.alany.common.core.annotation;

import com.alany.common.core.controller.model.ResponseResults;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope
@Aspect
public class RateLimitAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    //用来存放不同接口的RateLimiter
    private ConcurrentHashMap<String, RateLimiter> map = new ConcurrentHashMap<>();

    private RateLimiter rateLimiter;

    @Autowired
    private HttpServletResponse response;

    @Pointcut("@annotation(com.alany.core.annotation.RateLimit)")
    public void serviceAspect() {
    }


    @Around("serviceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Object obj = null;
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) joinPoint.getSignature();
        //返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //获取注解信息
        RateLimit annotation = currentMethod.getAnnotation(RateLimit.class);
        double limitNum = annotation.limitNum();
        String userParamName = annotation.limitParamName();
        String userValue = "";
        if (StringUtils.isNotBlank(userParamName)) {
            String[] parameterNames = msig.getParameterNames();
            for (int i = 0; i < parameterNames.length; i++) {
                if (parameterNames[i].equals(userParamName)) {
                    userValue = String.valueOf(joinPoint.getArgs()[i]);
                    break;
                }
            }
        }
        String key = currentMethod.getDeclaringClass().getName() + "." + currentMethod.getName()+ " " + userValue;
        //获取rateLimiter
        if (map.containsKey(key)) {
            rateLimiter = map.get(key);
        } else {
            map.put(key, RateLimiter.create(limitNum));
            rateLimiter = map.get(key);
        }

        try {
            if (rateLimiter.tryAcquire()) {
                //执行方法
                obj = joinPoint.proceed();
            } else {
                //拒绝了请求（服务降级）
                log.info("拒绝了请求：" + key);
                outErrorResult(ResponseResults.error(500, "系统繁忙"));
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;

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
