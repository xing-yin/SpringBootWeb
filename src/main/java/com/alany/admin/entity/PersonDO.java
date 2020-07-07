package com.alany.admin.entity;

import lombok.Data;

/**
 * @author yinxing
 * @date 2019/8/28
 */
@Data
public class PersonDO {

    private final Integer id;

    private final String username;

    public PersonDO(Integer id, String username) {
        this.id = id;
        this.username = username;
    }
}
