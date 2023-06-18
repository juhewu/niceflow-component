package com.niceflow.component.common.enums;

/**
 * 性别
 *
 * @author duanjw
 * @date 2023/5/19
 */
public enum GenderEnum implements BaseEnum<Integer> {
    MAN(1),
    WOMAN(2);
    private final Integer value;

    GenderEnum(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
