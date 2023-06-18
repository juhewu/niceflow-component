package com.niceflow.component.common.page;

import com.niceflow.component.common.enums.NumberEnum;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * 默认的分页请求
 *
 * @author duanjw
 * @date 2023/3/02
 */
@Setter
public class DefaultPageRequest implements PageRequest {

    /**
     * 每页默认显示的条数
     */
    public static final int DEFAULT_SIZE = 10;
    /**
     * 当前默认页
     */
    public static final int DEFAULT_CURRENT = 1;
    /**
     * 默认排序方式
     */
    public static final String DEFAULT_SORT = "1";

    /**
     * 每页显示的条数
     */
    private int pageSize;
    /**
     * 当前页
     */
    private int pageIndex;
    /**
     * 排序字段
     */
    private List<String> sorts;
    /**
     * 显示的控件
     */
    private List<String> showControls;

    /**
     * 获取排序字段
     *
     * @return 排序字段
     */
    @Override
    public List<String> getSorts() {
        if (null == sorts || sorts.isEmpty()) {
            return Collections.emptyList();
        }
        if (sorts.size() == NumberEnum.ZERO.getValue()) {
            sorts.add(DEFAULT_SORT);
            return sorts;
        }
        // 是否为偶数个数
        if (sorts.size() % NumberEnum.TWO.getValue() != NumberEnum.ZERO.getValue()) {
            throw new IllegalArgumentException();
        }
        return sorts;
    }

    /**
     * 获取显示的控件
     *
     * @return 显示的控件
     */
    @Override
    public List<String> getShowControls() {
        return showControls;
    }

    /**
     * 获取每页显示的条数
     * <p>
     * 默认 10 条
     *
     * @return 每页显示的条数
     */
    @Override
    public int getPageSize() {
        return pageSize <= 0 ? DEFAULT_SIZE : pageSize;
    }

    /**
     * 当前页
     * <p>
     * 默认第 1 页
     *
     * @return 当前页
     */
    @Override
    public int getPageIndex() {
        return pageIndex <= 0 ? DEFAULT_CURRENT : pageIndex;
    }
}
