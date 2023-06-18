package com.niceflow.component.captcha.core;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * 验证码提供者
 *
 * @author duanjw
 * @date 2023/6/13
 */
public interface CaptchaProvider {

    void fetch(ServletRequest request, ServletResponse response);

    boolean verify(String moduleId, ServletRequest request, ServletResponse response);
}
