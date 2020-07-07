package com.alany.common.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseService<T> extends IService<T> {

    IPage<T> selectPage(String field,
                        String keywords,
                        String orderBy,
                        String order,
                        int pageNum,
                        int limit);

    long findCount(String field,
                   String keywords);

}
