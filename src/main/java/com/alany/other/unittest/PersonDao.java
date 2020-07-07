package com.alany.other.unittest;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author yinxing
 * @date 2019/8/28
 */
@Mapper
public interface PersonDao {

    Person getPersonById(Integer id);


    void updatePerson(Person person);

}
