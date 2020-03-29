package com.csp.github.base.common.entity;

/**
 * @author 陈少平
 * @date 2019-12-22 10:31
 */
public class PageParam {

    private long pageNum = 1;
    private long pageSize = 10;

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNum() {
        if (pageNum < 1) {
            pageNum = 1;
        }
        return pageNum;
    }

    public long getPageSize() {
        if (pageSize < 1) {
            pageSize = 10;
        }
        return pageSize;
    }
}
