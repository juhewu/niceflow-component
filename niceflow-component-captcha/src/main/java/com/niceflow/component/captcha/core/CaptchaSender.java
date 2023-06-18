package com.niceflow.component.captcha.core;

import com.niceflow.component.captcha.core.Sender;

/**
 * 验证码发送
 *
 * @author duanjw
 * @date 2023/6/13
 */
public interface CaptchaSender {

    /**
     * 发送验证码
     *
     * @return
     */
    boolean send(Sender sender);

    /**
     * 是否支持
     *
     * @param type
     * @return
     */
    default boolean isSupport(String type) {
        return false;
    }
}
