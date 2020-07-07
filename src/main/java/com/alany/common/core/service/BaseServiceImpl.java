package com.alany.common.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Override
    public IPage<T> selectPage(String field,
                               String keywords,
                               String orderBy,
                               String order,
                               int pageNum,
                               int limit) {

        QueryWrapper ew = new QueryWrapper();
        setSearchWrapper(ew, field, keywords, null);
        setOrderBy(ew, orderBy, order);

        Page<T> page = new Page<T>(pageNum, limit);
        return baseMapper.selectPage(page, ew);
    }

    @Override
    public long findCount(String field,
                          String keywords) {
        QueryWrapper ew = new QueryWrapper();
        setSearchWrapper(ew, field, keywords, null);
        return baseMapper.selectCount(ew);
    }

    /**
     * 构造搜索条件
     */
    protected void setSearchWrapper(QueryWrapper wrapper,
                                    String field,
                                    String keywords,
                                    Integer status) {
        if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(keywords)) {
            wrapper.like(field, keywords);
        }
        if (status != null && status >= 0) {
            wrapper.eq("status", status);
        }
    }

    protected void setOrderBy(QueryWrapper<T> wrapper,
                              String orderBy,
                              String order) {
        if (StringUtils.isNotBlank(orderBy)) {
            if (StringUtils.isNotBlank(order) && order.equals("desc")) {
                wrapper.orderByDesc(orderBy);
            } else {
                wrapper.orderByAsc(orderBy);
            }
        }
    }

}
