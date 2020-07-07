package com.alany.other.unittest.example;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring boot 中使用 Junit
 *
 * @author yinxing
 * @date 2019/11/14
 */
// 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

    /**
     * 加入依赖
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-test</artifactId>
         <scope>test</scope>
     </dependency>
     */

}
