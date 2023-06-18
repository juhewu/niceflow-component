package com.niceflow.component.captcha.exception;

/**
 * 验证码未过期
 *
 * @author duanjw
 * @date 2023/6/15
 */
public class CaptchaNotExpired extends CaptchaException {
    public CaptchaNotExpired() {
    }

    public CaptchaNotExpired(String message) {
        super(message);
    }
}
