package com.alany.common.core.annotation;

import com.alany.common.util.RedisUtil;
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

import java.lang.reflect.Method;

@Component
@Scope
@Aspect
public class DistributedLockAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtil redisUtil;
    @Value("${scheduled.lock.is-open}")
    private boolean isOpen;
    @Value("${scheduled.lock.expire-time}")
    private int expectExpireTime;
    @Value("${security.user.platform}")
    private String platform;

    @Pointcut("execution(* com.alany.*.*..*(..)) && " +
            "(@annotation(org.springframework.scheduling.annotation.Scheduled) " +
            "|| @annotation(com.alany.common.core.annotation.DistributedLock))")
    public void serviceAspect() {
    }

    /**
     * 注解优于配置
     * 如果没有加注解，则读取properties文件配置，properties文件配置未配置，则使用默认值
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
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
        DistributedLock annotation = currentMethod.getAnnotation(DistributedLock.class);
        // 不加锁
        if (annotation == null && isOpen == false) {
            try {
                obj = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return obj;
        }

        // 加锁
        String lockKey = StringUtils.EMPTY;
        int expireTime = 360000;
        if (annotation != null) {
            lockKey = annotation.lockKey();
            expireTime = annotation.expireTime();
        } else if (isOpen == true) {
            lockKey = target.getClass().getName() + currentMethod.getName();
            expireTime = expectExpireTime;
        }

        // 默认优先采用平台号
        String requestId = platform;
        if (StringUtils.isEmpty(platform)) {
            requestId = "1";
        }
        if (redisUtil.tryGetDistributedLock(lockKey, requestId, expireTime)) {
            try {
                obj = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            redisUtil.releaseDistributedLock(lockKey, requestId);
        } else {
            logger.info(String.format("schedule lockKey %s find lock!", lockKey));
        }
        return obj;
    }

}
