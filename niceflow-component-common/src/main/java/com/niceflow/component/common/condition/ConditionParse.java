package com.niceflow.component.common.condition;

/**
 * 条件转换
 *
 * @author duanjw
 * @date 2023/3/8
 */
public interface ConditionParse {

    /**
     * 条件转换
     *
     * @param condition 条件
     * @return 条件字符串
     */
    String parse(Condition condition);
}
