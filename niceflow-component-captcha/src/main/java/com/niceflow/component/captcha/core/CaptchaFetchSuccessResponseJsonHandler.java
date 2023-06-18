package com.niceflow.component.captcha.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.SneakyThrows;

import java.io.PrintWriter;

/**
 * @author duanjw
 * @date 2023/6/14
 */
public class CaptchaFetchSuccessResponseJsonHandler implements CaptchaFetchSuccessHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void onSuccess(ServletRequest request, ServletResponse response, Captcha captcha) {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(captcha));
        writer.flush();
    }
}