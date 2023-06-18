package com.niceflow.component.common.page;

import java.util.List;

/**
 * 分页请求
 *
 * @author duanjw
 * @date 2023/3/02
 */
public interface PageRequest extends Slice {

    /**
     * 获取排序字段
     * <p>
     * 支持只传一个排序字段，按升序排序；如 sorts=name
     * 支持传一个字段和排序方式；如sorts=name,1
     * 支持传多个字段和每个字段的排序方式，如sorts=name,1,age,-1
     *
     * @return 排序字段
     */
    List<String> getSorts();

    /**
     * 设置排序字段
     *
     * @param sorts 排序字段
     */
    void setSorts(List<String> sorts);

    /**
     * 获取显示的控件
     *
     * @return 显示的控件
     */
    List<String> getShowControls();

    /**
     * 设置显示的控件
     *
     * @param showControls 显示的控件
     */
    void setShowControls(List<String> showControls);
}
