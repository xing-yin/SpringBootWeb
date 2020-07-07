package com.alany.admin.service.impl;

import com.alany.admin.entity.PersonDO;
import com.alany.admin.mapper.PersonMapper;
import com.alany.admin.service.PersonService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yinxing
 * @date 2019/8/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PersonServiceImpl extends ServiceImpl<PersonMapper, PersonDO> implements PersonService {

    @Override
    public List<PersonDO> findList() {
        QueryWrapper<PersonDO> qw = new QueryWrapper();
        return baseMapper.selectList(qw);
    }

    @Override
    public boolean savePersonDO(PersonDO personDO) {
        return this.saveOrUpdate(personDO);
    }

    @Override
    public boolean deletePersonDO(Long id) {
        if (baseMapper.deleteById(id) > 0) {
            return true;
        }
        return false;
    }
}
