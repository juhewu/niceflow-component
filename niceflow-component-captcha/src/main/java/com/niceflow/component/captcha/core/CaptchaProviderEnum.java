package com.niceflow.component.captcha.core;

/**
 * @author duanjw
 * @date 2023/4/25
 */
public enum CaptchaProviderEnum {
    NUMBER("1"),
    SLIDER("slider"),
    SMS("sms"),
    IMAGE("image"),
    ;
    private final String value;

    CaptchaProviderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
