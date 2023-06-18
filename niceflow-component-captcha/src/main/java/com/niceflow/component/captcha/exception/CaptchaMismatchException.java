package com.niceflow.component.captcha.exception;

/**
 * 验证码不匹配异常
 *
 * @author duanjw
 * @date 2023/6/17
 */
public class CaptchaMismatchException extends CaptchaException {
    public CaptchaMismatchException() {
        this("验证码输入错误，请修改后重试");
    }

    public CaptchaMismatchException(String message) {
        super(message);
    }
}
