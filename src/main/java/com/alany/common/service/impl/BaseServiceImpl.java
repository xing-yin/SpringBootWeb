package com.alany.common.service.impl;

import com.alany.common.service.BaseService;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yinxing on 2018/6/22.
 */
@Service
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public Page<T> selectByPage(int pageNo,
                                int pageSize,
                                Integer status,
                                String property,
                                String keywords,
                                String orderBy,
                                String sequence) {
        Page<T> page = new Page<>(pageNo, pageSize);
        page.setOptimizeCount(true);

        EntityWrapper ew = new EntityWrapper();
        setSearchCondition(ew, status, property, keywords);
        setOderByCondition(ew, orderBy, sequence);

        List<T> list = baseMapper.selectPage(page, ew);
        page.setTotal(totalCount(status, property, keywords));
        page.setRecords(list);
        return page;
    }

    private int totalCount(Integer status, String property, String keywords) {
        EntityWrapper ew = new EntityWrapper();
        setSearchCondition(ew, status, property, keywords);
        return baseMapper.selectCount(ew);
    }

    /**
     * 设置搜索条件
     *
     * @param ew
     * @param status
     * @param property
     * @param keywords
     */
    private void setSearchCondition(EntityWrapper ew,
                                    Integer status,
                                    String property,
                                    String keywords) {
        if (status != null) {
            ew.eq("status", status);
        }
        if (StringUtils.isNotBlank(property) && StringUtils.isNotBlank(keywords)) {
            ew.like(property, keywords);
        }
    }

    /**
     * 设置排序条件
     *
     * @param ew
     * @param orderBy
     * @param sequence 升序/倒序
     */
    private void setOderByCondition(EntityWrapper ew, String orderBy, String sequence) {
        if (StringUtils.isNotBlank(orderBy) && sequence.equals("asc")) {
            ew.orderBy(orderBy, true);
        } else {
            ew.orderBy(orderBy, false);
        }
    }

}
