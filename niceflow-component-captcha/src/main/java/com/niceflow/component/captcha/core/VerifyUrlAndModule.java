package com.niceflow.component.captcha.core;

import com.niceflow.component.captcha.config.AntPathRequestMatcher;
import lombok.Data;

/**
 * @author duanjw
 * @date 2023/6/14
 */
@Data
public class VerifyUrlAndModule {
    private String moduleId;
    private AntPathRequestMatcher antPathRequestMatcher;

    public VerifyUrlAndModule(String moduleId, AntPathRequestMatcher antPathRequestMatcher) {
        this.moduleId = moduleId;
        this.antPathRequestMatcher = antPathRequestMatcher;
    }
}
