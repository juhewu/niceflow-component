package com.niceflow.component.captcha.core;

import lombok.Data;

/**
 * 发送者
 *
 * @author duanjw
 * @date 2023/6/13
 */
@Data
public class Sender {

    /**
     * 接受者
     */
    private String receiver;

    /**
     * 类型
     * <p>
     * 短信、邮件
     */
    private String type;

    /**
     * 模板 code
     */
    private String templateCode;

    /**
     * 验证码
     */
    private String captcha;

}
