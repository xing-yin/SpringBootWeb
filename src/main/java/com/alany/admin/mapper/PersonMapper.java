package com.alany.admin.mapper;

import com.alany.admin.entity.PersonDO;
import com.alany.other.unittest.Person;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author yinxing
 * @date 2019/8/28
 */
@Mapper
public interface PersonMapper extends BaseMapper<PersonDO> {

    Person getPersonById(Integer id);

    void updatePerson(Person person);

}
