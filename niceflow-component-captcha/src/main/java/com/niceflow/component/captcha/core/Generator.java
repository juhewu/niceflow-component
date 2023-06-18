package com.niceflow.component.captcha.core;

import lombok.Data;

/**
 * 生成配置
 *
 * @author duanjw
 * @date 2023/6/13
 */
@Data
public class Generator {

    /**
     * key 模式
     */
    private String keyPattern = "${moduleId}_${username}_${system.uuid}";

    /**
     * 类型
     * <p>
     * 数字验证码、缺块滑块验证码
     */
    private String type;

    /**
     * 长度
     */
    private int length;
}
