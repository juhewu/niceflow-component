package com.niceflow.component.captcha.core;

/**
 * 验证码预处理
 *
 * @author duanjw
 * @date 2023/6/13
 */
public interface CaptchaPreprocessor {
    boolean check();

    default boolean isSupport() {
        return false;
    }
}
