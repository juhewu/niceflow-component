package com.niceflow.component.common.page;

/**
 * 部分
 *
 * @author duanjw
 * @date 2023/3/02
 */
public interface Slice {

    /**
     * 每页大小
     *
     * @return 每页大小
     */
    int getPageSize();

    /**
     * 当前页
     *
     * @return 当前页
     */
    int getPageIndex();
}
