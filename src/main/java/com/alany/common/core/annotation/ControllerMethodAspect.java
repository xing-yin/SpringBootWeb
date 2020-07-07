package com.alany.common.core.annotation;

import com.alany.common.core.util.CommonSysLogUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class ControllerMethodAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.alany.common.core.annotation.OpenApi)" +
            "|| execution(* com.alany..controller..*.*(..)) " +
            "&& (@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping))")
    public void serviceAspect() {
    }

    @Around("serviceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURI();
        //获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method currentMethod = signature.getMethod();
        OpenApi annotation = currentMethod.getAnnotation(OpenApi.class);
        if (StringUtils.isNotBlank(url) && (url.contains("/api/") || url.contains("/openapi/"))
                || (annotation != null && annotation.isAPI())) {

            Long requestTime = System.currentTimeMillis();
            String params = CommonSysLogUtil.paramsSubstring(request.getParameterMap());
            String appId = request.getParameter("appId");

            CommonSysLogUtil.printApiRequestLog(appId, url, params);
            try {
                obj = joinPoint.proceed();
                CommonSysLogUtil.printApiResponseLog(appId, url, params, obj, System.currentTimeMillis() - requestTime);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                logger.error(throwable.toString());
            }
        } else {
            try {
                obj = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                logger.error(throwable.toString());
            }
        }
        return obj;
    }

}
