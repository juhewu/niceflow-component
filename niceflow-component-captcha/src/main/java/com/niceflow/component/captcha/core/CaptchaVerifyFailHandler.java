package com.niceflow.component.captcha.core;

import com.niceflow.component.captcha.exception.CaptchaException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * 验证码验证失败
 *
 * @author duanjw
 * @date 2023/6/15
 */
public interface CaptchaVerifyFailHandler {
    void verifyFail(ServletRequest request, ServletResponse response, CaptchaException captchaException);

}
