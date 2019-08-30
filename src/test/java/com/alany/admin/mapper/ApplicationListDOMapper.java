package com.alany.admin.mapper;

import com.alany.admin.model.ApplicationListDO;

public interface ApplicationListDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationListDO record);

    int insertSelective(ApplicationListDO record);

    ApplicationListDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplicationListDO record);

    int updateByPrimaryKey(ApplicationListDO record);
}