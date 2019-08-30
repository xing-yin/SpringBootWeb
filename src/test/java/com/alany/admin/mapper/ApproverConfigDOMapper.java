package com.alany.admin.mapper;

import com.alany.admin.model.ApproverConfigDO;

public interface ApproverConfigDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApproverConfigDO record);

    int insertSelective(ApproverConfigDO record);

    ApproverConfigDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApproverConfigDO record);

    int updateByPrimaryKey(ApproverConfigDO record);
}