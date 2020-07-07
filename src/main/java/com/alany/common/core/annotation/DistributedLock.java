package com.alany.common.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    String lockKey();

    /**
     * 超期时间单位(ms)
     */
    int expireTime() default 3600000;
}

