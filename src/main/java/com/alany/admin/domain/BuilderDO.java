package com.alany.admin.domain;

import lombok.Builder;

/**
 * '@Builder' 注解代替构建者模式
 *
 * @author yinxing
 * @date 2019/9/11
 */

@Builder
public class BuilderDO {

    private Integer id;

    private String firstName;

    private String lastName;

    public static void main(String[] args) {
        BuilderDO builderDO = BuilderDO
                .builder()
                .firstName("firstName")
                .lastName("lastName")
                .build();
        System.out.println(builderDO.firstName);
        System.out.println(builderDO.lastName);
    }
}
