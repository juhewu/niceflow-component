package com.niceflow.component.captcha.core;

import lombok.Data;

/**
 * 验证码
 *
 * @author duanjw
 * @date 2023/4/18
 */
@Data
public class Captcha {

    /**
     * 唯一 key
     */
    private String key;

    /**
     * 类型
     */
    private String type;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 值
     */
    private String value;

    public Captcha(String secret, String value) {
        this.secret = secret;
        this.value = value;
    }

    public Captcha(String key, String secret, String value) {
        this.key = key;
        this.secret = secret;
        this.value = value;
    }

    public Captcha(String secret) {
        this.secret = secret;
    }
}
