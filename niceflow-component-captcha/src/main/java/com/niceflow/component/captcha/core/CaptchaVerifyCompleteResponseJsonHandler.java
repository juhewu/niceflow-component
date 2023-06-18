package com.niceflow.component.captcha.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niceflow.component.common.utils.R;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author duanjw
 * @date 2023/4/24
 */
@Slf4j
public class CaptchaVerifyCompleteResponseJsonHandler implements CaptchaVerifyCompleteHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void onComplete(ServletRequest request, ServletResponse response, boolean verified) {
        if (!verified) {
            response.setContentType("application/json;charset=utf-8");
            try (PrintWriter writer = response.getWriter()) {
                writer.write(objectMapper.writeValueAsString(R.failed("验证码填写错误，请修改后重试")));
                writer.flush();
            } catch (IOException e) {
                log.error("返回验证码结果，关闭 writer 出现异常", e);
            }
        }
    }
}