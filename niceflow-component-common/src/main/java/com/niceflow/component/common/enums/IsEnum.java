package com.niceflow.component.common.enums;

/**
 * 是 否
 *
 * @author duanjw
 * @date 2023/4/2
 */
public enum IsEnum {
    /**
     * 是
     */
    YES(true, 1),
    /**
     * 否
     */
    NO(false, 0);
    private final Boolean booleanValue;
    private final Integer integerValue;

    IsEnum(Boolean booleanValue, Integer integerValue) {
        this.booleanValue = booleanValue;
        this.integerValue = integerValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }
}
