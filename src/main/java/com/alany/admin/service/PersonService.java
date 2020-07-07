package com.alany.admin.service;

import com.alany.admin.entity.PersonDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author yinxing
 * @date 2019/11/13
 */

public interface PersonService extends IService<PersonDO> {
    
    List<PersonDO> findList();
    
    boolean savePersonDO(PersonDO personDO);

    boolean deletePersonDO(Long id);
}
