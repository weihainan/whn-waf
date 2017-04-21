package com.whn.waf.common.support;


/**
 * 分页数据
 *
 * @author weihainan.
 * @since 0.1 created on 2017/4/1.
 */
public class PageParams {

    /*----------------------- page size -------------------*/

    private long page = 0;
    private long size = 10;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        if (page > 0) {
            this.page = page;
        }
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        if (size > 0) {
            this.size = size;
        }
    }

    /*----------------------- limit offset -- 建议使用 -------------------*/

    private long limit = 10;
    private long offset = 0;

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        if (limit > 0) {
            this.limit = limit;
        }
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        if (offset > 0) {
            this.offset = offset;
        }
    }

    public long getPageNumber() {
        return offset / limit;
    }

    public long getPageSize() {
        return limit;
    }


    /*-----------------------  total page -------------------*/

    public int getTotalPages(long total, long size) {
        return size == 0 ? 1 : (int) Math.ceil((double) total / (double) size);
    }
}
