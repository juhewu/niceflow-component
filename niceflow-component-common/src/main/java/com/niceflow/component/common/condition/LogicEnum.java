package com.niceflow.component.common.condition;

/**
 * 逻辑操作符
 *
 * @author duanjw
 * @date 2023/3/30
 */
public enum LogicEnum {
    /**
     * 并且
     */
    AND(1, "and"),
    /**
     * 或者
     */
    OR(2, "or"),
    /**
     * 非
     */
    NOT(3, "not"),
    /**
     *
     */
    NOR(4, "nor"),
    /**
     * 等于
     */
    EQ(5, "eq"),
    /**
     * 大于
     */
    GT(6, "gt"),
    /**
     * 大于等于
     */
    GTE(7, "gte"),
    /**
     * 包含
     */
    IN(8, "in"),
    /**
     * 小于
     */
    LT(9, "lt"),
    /**
     * 小于等于
     */
    LTE(10, "lte"),
    /**
     * 不等于
     */
    NE(11, "ne"),
    /**
     * 不包含
     */
    NIN(12, "nin"),
    /**
     * 正则匹配
     */
    REGEX(13, "regex");

    private final Integer id;
    private final String value;

    LogicEnum(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public static LogicEnum get(int id) {
        for (LogicEnum value : values()) {
            if (id == value.id) {
                return value;
            }
        }
        throw new RuntimeException();
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}