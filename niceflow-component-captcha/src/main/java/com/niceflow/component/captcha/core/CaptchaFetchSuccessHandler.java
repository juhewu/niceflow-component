package com.niceflow.component.captcha.core;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * 获取验证码成功
 *
 * @author duanjw
 * @date 2023/4/24
 */
public interface CaptchaFetchSuccessHandler {
    default void onSuccess(ServletRequest request, ServletResponse response, Captcha captcha) {
    }
}