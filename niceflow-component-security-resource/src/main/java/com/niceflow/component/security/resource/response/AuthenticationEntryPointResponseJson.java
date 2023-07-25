package com.niceflow.component.security.resource.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
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
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        if (authException instanceof OAuth2AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException) authException).getError();

            if (error instanceof BearerTokenError) {
                status = ((BearerTokenError) error).getHttpStatus();
            }
        }
        response.setStatus(status.value());
        responseFailed(request, response, "登录超时，请重新登录", status.value());
    }
}
