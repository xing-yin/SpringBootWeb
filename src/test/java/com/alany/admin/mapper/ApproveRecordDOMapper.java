package com.alany.admin.mapper;

import com.alany.admin.model.ApproveRecordDO;

public interface ApproveRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApproveRecordDO record);

    int insertSelective(ApproveRecordDO record);

    ApproveRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApproveRecordDO record);

    int updateByPrimaryKey(ApproveRecordDO record);
}