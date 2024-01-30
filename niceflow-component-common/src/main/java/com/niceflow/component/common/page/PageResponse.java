package com.niceflow.component.common.page;

import java.util.List;

/**
 * 分页响应
 *
 * @author duanjw
 * @date 2023/3/8
 */
public interface PageResponse<T> extends Slice {

    /**
     * 获取总条数
     *
     * @return 总条数
     */
    long getTotal();

    /**
     * 获取页码
     * <p>
     * 默认根据总条数和每页显示数量计算出
     *
     * @return 页码
     */
    default int page() {
        if (this.getPageSize() == 0) {
            return 0;
        } else {
            long pages = this.getTotal() / this.getPageSize();
            if (this.getTotal() % this.getPageSize() != 0L) {
                ++pages;
            }

            return (int) pages;
        }
    }

    /**
     * 获取数据列表
     *
     * @return 数据列表
     */
    List<T> getValue();

    /**
     * 设置数据列表
     *
     * @param list 数据列表
     */
    void setValue(List<T> list);
}