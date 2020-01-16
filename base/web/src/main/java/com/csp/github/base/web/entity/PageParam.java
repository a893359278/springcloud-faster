package com.csp.github.base.web.entity;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author 陈少平
 * @date 2019-12-22 10:31
 */
@Setter
@Accessors(chain = true)
public class PageParam {
    private long pageNum = 1;
    private long pageSize = 10;

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
