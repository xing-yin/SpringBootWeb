package com.alany.common.service.impl;

import com.alany.common.service.BaseService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yinxing on 2018/6/22.
 */
@Service
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public IPage<T> selectByPage(int pageNo,
                                 int pageSize,
                                 Integer status,
                                 String property,
                                 String keywords,
                                 String orderBy,
                                 String sequence) {
        IPage<T> page = new Page<T>(pageNo, pageSize) {
        };

        QueryWrapper ew = new QueryWrapper();
        setSearchCondition(ew, status, property, keywords);
        setOderByCondition(ew, orderBy, sequence);

        IPage<T> iPage = baseMapper.selectPage(page, ew);
        List<T> list = iPage.getRecords();
        page.setTotal(totalCount(status, property, keywords));
        page.setRecords(list);
        return page;
    }

    private int totalCount(Integer status, String property, String keywords) {
        QueryWrapper ew = new QueryWrapper();
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
    private void setSearchCondition(QueryWrapper ew,
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
    private void setOderByCondition(QueryWrapper ew, String orderBy, String sequence) {
        if (StringUtils.isNotBlank(orderBy) && sequence.equals("asc")) {
            ew.orderBy(true, true, orderBy);
        } else {
            ew.orderBy(true, false, orderBy);
        }
    }

}
