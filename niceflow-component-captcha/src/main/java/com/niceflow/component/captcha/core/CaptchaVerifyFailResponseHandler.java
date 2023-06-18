package com.niceflow.component.captcha.core;

import com.niceflow.component.captcha.exception.CaptchaException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

/**
 * @author duanjw
 * @date 2023/6/16
 */
public class CaptchaVerifyFailResponseHandler implements CaptchaVerifyFailHandler {
    @Override
    @SneakyThrows
    public void verifyFail(ServletRequest request, ServletResponse response, CaptchaException captchaException) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(true);
        if (session != null) {
            httpRequest.getSession().setAttribute("CAPTCHA_VERIFY_LAST_EXCEPTION", captchaException);
        }
        httpResponse.sendRedirect("/login#captcha-tab");

    }
}
