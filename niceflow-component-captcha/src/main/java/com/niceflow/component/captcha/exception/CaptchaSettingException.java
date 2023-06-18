package com.niceflow.component.captcha.exception;

/**
 * 验证码配置错误
 *
 * @author duanjw
 * @date 2023/6/15
 */
public class CaptchaSettingException extends CaptchaException {

    public CaptchaSettingException() {
    }

    public CaptchaSettingException(String message) {
        super(message);
    }
}
