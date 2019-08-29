package com.alany.other.unittest;

import lombok.Data;

/**
 * @author yinxing
 * @date 2019/8/28
 */
@Data
public class Person {

    private final Integer id;

    private final String username;

    public Person(Integer id, String username) {
        this.id = id;
        this.username = username;
    }
}
