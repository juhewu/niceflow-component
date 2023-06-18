package com.niceflow.component.captcha.core;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public interface CaptchaVerifyCompleteHandler {
    void onComplete(ServletRequest request, ServletResponse response, boolean verified);
}
