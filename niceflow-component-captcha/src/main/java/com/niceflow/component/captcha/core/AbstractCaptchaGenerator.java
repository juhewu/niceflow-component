package com.niceflow.component.captcha.core;

/**
 * @author duanjw
 * @date 2023/6/14
 */
public abstract class AbstractCaptchaGenerator implements CaptchaGenerator {
    @Override
    public boolean verify(String type, String input, String secret) {
        return input.equals(secret);
    }
}
