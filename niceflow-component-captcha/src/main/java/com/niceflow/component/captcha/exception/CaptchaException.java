package com.niceflow.component.captcha.exception;

/**
 * 验证码异常
 *
 * @author duanjw
 * @date 2023/6/15
 */
public class CaptchaException extends RuntimeException {

    public CaptchaException() {
    }

    public CaptchaException(String message) {
        super(message);
    }
}
