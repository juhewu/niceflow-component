package com.niceflow.component.captcha.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niceflow.component.captcha.exception.CaptchaException;
import com.niceflow.component.common.utils.R;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author duanjw
 * @date 2023/6/15
 */
@Slf4j
public class CaptchaVerifyFailResponseJsonHandler implements CaptchaVerifyFailHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void verifyFail(ServletRequest request, ServletResponse response, CaptchaException captchaException) {
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(R.failed(captchaException.getMessage())));
            writer.flush();
        } catch (IOException e) {
            log.error("返回验证码结果，关闭 writer 出现异常", e);
        }
    }
}
