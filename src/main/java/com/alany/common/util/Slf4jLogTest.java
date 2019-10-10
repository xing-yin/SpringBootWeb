package com.alany.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Slf4j 日志测试
 *
 * @author yinxing
 * @date 2019/10/10
 */
@Slf4j
public class Slf4jLogTest {

    /**
     * 测试日志打印格式
     *
     * @param args
     */
    public static void main(String[] args) {
        log.info("test string1 {}", "string1");
        log.info("test int1 {}", 1);
        log.info(String.format("test int2 %d ", 1));
        log.info(String.format("test string2 %s ", "string2"));
    }
}
