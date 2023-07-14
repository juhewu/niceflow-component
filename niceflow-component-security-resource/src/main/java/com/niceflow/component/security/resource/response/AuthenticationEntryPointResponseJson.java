package com.niceflow.component.security.resource.response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * token 过期或不正确处理
 *
 * @author duanjw
 * @date 2023/7/9
 */
public class AuthenticationEntryPointResponseJson extends AbstractAuthenticationResponseJson implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        responseFailed(request, response, "登录超时，请重新登录", 2);
    }
}
