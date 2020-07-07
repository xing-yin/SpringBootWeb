package com.alany.common.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("插入方法实体填充");

        for (String name : metaObject.getSetterNames()) {
            System.out.println(name);
        }

        metaObject.setValue("create_time", new Date());
        metaObject.setValue("update_time", new Date());

        setFieldValByName("create_time", new Date(), metaObject);
        setFieldValByName("update_time", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("更新方法实体填充");
        for (String name : metaObject.getSetterNames()) {
            System.out.println(name);
        }

        // 测试下划线
        Object testType = getFieldValByName("updateTime", metaObject);
        System.out.println("updateTime=" + testType);

        setFieldValByName("updateTime", new Date(), metaObject);
        setFieldValByName("update_time", new Date(), metaObject);

        // 测试下划线
        Object testType2 = getFieldValByName("updateTime", metaObject);
        System.out.println("updateTime=" + testType2);
    }

}
