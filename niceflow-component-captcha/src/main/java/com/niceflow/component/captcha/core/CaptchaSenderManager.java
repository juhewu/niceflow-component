package com.niceflow.component.captcha.core;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 验证码发送管理
 *
 * @author duanjw
 * @date 2023/6/13
 */
@AllArgsConstructor
public class CaptchaSenderManager implements CaptchaSender {
    private final List<CaptchaSender> captchaSenders;

    @Override
    public boolean send(Sender sender) {
        for (CaptchaSender captchaSender : captchaSenders) {
            if (captchaSender.isSupport(sender.getType())) {
                return captchaSender.send(sender);
            }
        }
        return false;
    }

}
