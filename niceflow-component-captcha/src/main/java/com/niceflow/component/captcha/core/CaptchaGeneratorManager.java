package com.niceflow.component.captcha.core;

import com.niceflow.component.captcha.utils.VariableUtil;
import jakarta.servlet.ServletRequest;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 验证码生成管理
 *
 * @author duanjw
 * @date 2023/6/13
 */
@AllArgsConstructor
public class CaptchaGeneratorManager implements CaptchaGenerator {
    private final List<CaptchaGenerator> captchaGenerators;

    @Override
    public Captcha generate(Generator generator) {

        for (CaptchaGenerator captchaGenerator : captchaGenerators) {
            if (captchaGenerator.isSupport(generator.getType())) {
                Captcha captcha = captchaGenerator.generate(generator);
                captcha.setType(generator.getType());
                return captcha;
            }
        }
        throw new RuntimeException("没有可用的验证码生成器，不能生成验证码");
    }

    @Override
    public boolean verify(String type, String input, String secret) {

        for (CaptchaGenerator captchaGenerator : captchaGenerators) {
            if (captchaGenerator.isSupport(type)) {
                return captchaGenerator.verify(type, input, secret);
            }
        }
        throw new RuntimeException("没有可用的验证码校验器，不能校验验证码");
    }

    @Override
    public String getKey(Generator generator, ServletRequest request) {
        String keyPattern = generator.getKeyPattern();
        return VariableUtil.replace(keyPattern, request);
    }
}
