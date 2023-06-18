package com.niceflow.component.captcha.filter;

import com.niceflow.component.captcha.core.CaptchaProvider;
import jakarta.servlet.*;

import java.io.IOException;

/**
 * 验证码获取
 *
 * @author duanjw
 * @date 2023/4/18
 */
public class CaptchaFetchFilter implements Filter {

    private final CaptchaProvider captchaProvider;

    public CaptchaFetchFilter(CaptchaProvider captchaProvider) {
        this.captchaProvider = captchaProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        captchaProvider.fetch(request, response);
    }
}
