package com.niceflow.component.captcha.core;

import com.niceflow.component.captcha.core.Captcha;
import com.niceflow.component.captcha.core.Generator;
import jakarta.servlet.ServletRequest;

/**
 * @author duanjw
 * @date 2023/4/24
 */
public interface CaptchaGenerator {
    Captcha generate(Generator generator);

    default String getKey(Generator generator, ServletRequest request) {
        return generator.getKeyPattern();
    }

    default boolean isSupport(String type) {
        return false;
    }

    boolean verify(String type, String input, String secret);
}
