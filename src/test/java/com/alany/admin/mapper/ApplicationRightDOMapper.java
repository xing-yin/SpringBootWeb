package com.alany.admin.mapper;

import com.alany.admin.model.ApplicationRightDO;

public interface ApplicationRightDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationRightDO record);

    int insertSelective(ApplicationRightDO record);

    ApplicationRightDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplicationRightDO record);

    int updateByPrimaryKey(ApplicationRightDO record);
}