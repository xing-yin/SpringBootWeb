package com.alany.mybatis.simpleDemo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yinxing
 * @date 2019/8/28
 */
@Mapper
public interface PersonMapper {

    List<Person> findListById(Integer id);

}
