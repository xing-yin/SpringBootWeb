package com.alany.common.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * Created by yinxing on 2018/6/22.
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 通用分页查询（可搜索排序）
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @param status   状态:有效/无效
     * @param property 属性(字段)名
     * @param keywords 搜索关键词
     * @param orderBy  排序条件
     * @param sequence 顺序:升序/降序
     * @return
     */
    Page<T> selectByPage(int pageNo,
                         int pageSize,
                         Integer status,
                         String property,
                         String keywords,
                         String orderBy,
                         String sequence);

}
