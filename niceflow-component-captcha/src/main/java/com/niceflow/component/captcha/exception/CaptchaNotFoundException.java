package com.niceflow.component.captcha.exception;

/**
 * 验证码不能存在
 *
 * @author duanjw
 * @date 2023/6/15
 */
public class CaptchaNotFoundException extends CaptchaException {

    public CaptchaNotFoundException() {
        super("输入的验证码无效，请重新获取验证码");
    }

    public CaptchaNotFoundException(String message) {
        super(message);
    }
}
