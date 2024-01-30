package com.niceflow.component.common.page;

import com.niceflow.component.common.utils.AbstractRefResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认分页响应
 *
 * @param <T> T
 * @author duanjw
 * @date 2023/3/30
 */
public class DefaultPageResponse<T> extends AbstractRefResponse implements PageResponse<T> {

    /**
     * 总条数
     */
    protected long total;
    /**
     * 数据列表
     */
    protected List<T> value = new ArrayList<>();
    /**
     * 每页大小
     */
    protected int pageSize;
    /**
     * 当前页
     */
    protected int pageIndex;

    public DefaultPageResponse() {
    }

    public DefaultPageResponse(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public DefaultPageResponse(List<T> value, long total, int pageIndex, int pageSize) {
        this.total = total;
        this.value = value;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public DefaultPageResponse(List<T> value, long total, PageRequest pageRequest) {
        this.total = total;
        this.value = value;
        this.pageSize = pageRequest.getPageSize();
        this.pageIndex = pageRequest.getPageIndex();
    }

    public DefaultPageResponse(PageRequest pageRequest) {
        this.total = 0;
        this.pageSize = pageRequest.getPageSize();
        this.pageIndex = pageRequest.getPageIndex();
    }

    /**
     * 获取总条数
     *
     * @return 总条数
     */
    @Override
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 获取数据列表
     *
     * @return 数据列表
     */
    @Override
    public List<T> getValue() {
        return value;
    }

    /**
     * 设置数据列表
     *
     * @param list 数据列表
     */
    @Override
    public void setValue(List<T> list) {
        this.value = list;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
