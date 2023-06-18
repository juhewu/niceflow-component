package com.niceflow.component.common.page;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询
 *
 * @param <T> 任意对象
 * @author duanjw
 * @date 2023/3/8
 */
@Getter
@Setter
public class PageQuery<T> {

    /**
     * 查询条件
     */
    private T query;
    /**
     * 分页参数
     */
    private PageRequest pageRequest;

    public PageQuery(T query, PageRequest pageRequest) {
        this.query = query;
        this.pageRequest = pageRequest;
    }

    public PageQuery(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }
}
