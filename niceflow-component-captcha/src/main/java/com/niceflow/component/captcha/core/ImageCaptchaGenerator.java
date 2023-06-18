package com.niceflow.component.captcha.core;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niceflow.component.common.utils.R;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 图形验证码
 *
 * @author duanjw
 * @date 2023/6/13
 */
public class ImageCaptchaGenerator extends AbstractCaptchaGenerator {
    @Override
    public Captcha generate(Generator generator) {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(116, 36, generator.getLength(), 5);
        return new Captcha(circleCaptcha.getCode(), circleCaptcha.getImageBase64());
    }

    @Override
    public boolean isSupport(String type) {
        return CaptchaProviderEnum.IMAGE.getValue().equals(type);
    }









}
