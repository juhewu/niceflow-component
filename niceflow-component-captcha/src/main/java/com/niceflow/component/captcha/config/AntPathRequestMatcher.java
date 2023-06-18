package com.niceflow.component.captcha.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

/**
 * @author duanjw
 * @date 2023/4/23
 */
public class AntPathRequestMatcher extends AntPathMatcher {
    private static final String MATCH_ALL = "/**";

    private final String httpMethod;
    private final String pattern;
    public AntPathRequestMatcher(String pattern, String httpMethod) {
        this.pattern = pattern;
        this.httpMethod = httpMethod;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public boolean match(HttpServletRequest request) {
        if (this.httpMethod != null && StringUtils.hasText(request.getMethod())
                && !this.httpMethod.equals(request.getMethod())) {
            return false;
        }
        if (this.pattern.equals(MATCH_ALL)) {
            return true;
        }
        return super.match(pattern, getRequestPath(request));
    }
    private String getRequestPath(HttpServletRequest request) {

        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            url = StringUtils.hasLength(url) ? url + pathInfo : pathInfo;
        }
        return url;
    }
}
