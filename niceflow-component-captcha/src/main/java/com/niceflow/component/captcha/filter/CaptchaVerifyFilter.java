package com.niceflow.component.captcha.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.niceflow.component.captcha.config.AntPathRequestMatcher;
import com.niceflow.component.captcha.core.CaptchaProvider;
import com.niceflow.component.captcha.core.VerifyUrlAndModule;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 验证码校验
 *
 * @author duanjw
 * @date 2023/4/18
 */
@Slf4j
public class CaptchaVerifyFilter implements Filter {
    private final List<VerifyUrlAndModule> verifyUrlAndModules;

    private final CaptchaProvider captchaProvider;

    public CaptchaVerifyFilter(List<VerifyUrlAndModule> verifyUrlAndModules, CaptchaProvider captchaProvider) {
        this.verifyUrlAndModules = verifyUrlAndModules;
        this.captchaProvider = captchaProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        TimeInterval start = DateUtil.timer();
        if (Objects.isNull(verifyUrlAndModules) || verifyUrlAndModules.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean verified = true;
        for (VerifyUrlAndModule verifyUrlAndModule : verifyUrlAndModules) {
            AntPathRequestMatcher antPathRequestMatcher = verifyUrlAndModule.getAntPathRequestMatcher();
            boolean match = antPathRequestMatcher.match((HttpServletRequest) request);
            if (match) {
                verified = captchaProvider.verify(verifyUrlAndModule.getModuleId(), request, response);
                break;
            }
        }
        log.debug("校验验证码耗时：{} 毫秒", start.intervalMs());
        if (!verified) {
            return;
        }
        filterChain.doFilter(request, response);
    }
}
