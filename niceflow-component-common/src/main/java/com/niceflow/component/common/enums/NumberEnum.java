package com.niceflow.component.common.enums;

/**
 * 数字
 *
 * @author duanjw
 * @date 2023/5/19
 */
public enum NumberEnum implements BaseEnum<Integer> {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    private final Integer value;

    NumberEnum(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
