package com.niceflow.component.captcha.core;

import lombok.Data;

/**
 * 校验验证码
 *
 * @author duanjw
 * @date 2023/6/14
 */
@Data
public class Verify {

    /**
     * url
     */
    private String url;
    /**
     * http 方法
     */
    private String httpMethod;
}
