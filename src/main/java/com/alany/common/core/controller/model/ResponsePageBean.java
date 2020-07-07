package com.alany.common.core.controller.model;

import lombok.Getter;

/**
 * api 接口的 page 格式规范
 */
@Getter
public class ResponsePageBean {

    /**
     * 总数
     */
    private int totalCount;
    /**
     * 页码
     */
    private int pageNo;
    /**
     * 每页条数
     */
    private int pageSize;

    public ResponsePageBean setTotal(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public ResponsePageBean setPageNum(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public ResponsePageBean setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

}
