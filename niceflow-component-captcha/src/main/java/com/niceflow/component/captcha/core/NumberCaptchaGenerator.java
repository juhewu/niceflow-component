package com.niceflow.component.captcha.core;

import cn.hutool.core.util.RandomUtil;

/**
 * 数字验证码
 *
 * @author duanjw
 * @date 2023/6/13
 */
public class NumberCaptchaGenerator extends AbstractCaptchaGenerator {

    @Override
    public Captcha generate(Generator generator) {
        return new Captcha(RandomUtil.randomNumbers(generator.getLength()));
    }

    @Override
    public boolean isSupport(String type) {
        return CaptchaProviderEnum.NUMBER.getValue().equals(type);
    }
}
