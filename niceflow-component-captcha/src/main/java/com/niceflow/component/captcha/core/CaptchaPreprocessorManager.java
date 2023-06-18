package com.niceflow.component.captcha.core;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 验证码预处理管理
 *
 * @author duanjw
 * @date 2023/6/13
 */
@AllArgsConstructor
public class CaptchaPreprocessorManager implements CaptchaPreprocessor {
    private final List<CaptchaPreprocessor> captchaPreprocessors;

    @Override
    public boolean check() {
        for (CaptchaPreprocessor captchaPreprocessor : captchaPreprocessors) {
            if (captchaPreprocessor.isSupport()) {
                return captchaPreprocessor.check();
            }
        }
        return true;
    }
}
