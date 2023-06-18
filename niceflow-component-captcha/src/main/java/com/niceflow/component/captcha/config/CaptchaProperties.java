package com.niceflow.component.captcha.config;

import java.util.Map;

import com.niceflow.component.captcha.core.Generator;
import com.niceflow.component.captcha.core.Sender;
import com.niceflow.component.captcha.core.Verify;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author duanjw
 * @date 2023/4/18
 */
@Data
@ConfigurationProperties(prefix = "juhewu")
public class CaptchaProperties {

    private Map<String, CaptchaConfig> captcha;

    @Getter
    @Setter
    public static class CaptchaConfig {

        private Generator generator;
        private Sender sender;
        private Verify verify;
//
//        private String url;
//
//        private String captchaKeyPattern = "${username}_";
//
//        /**
//         * 验证码发送渠道
//         *
//         * 邮件
//         */
//        private String sendChannel;
//
//        /**
//         * 类型
//         *
//         * 数字、缺块验证码
//         */
//        private String type;
//
//        /**
//         * 验证码长度
//         */
//        private Integer length;
//
//        /**
//         * 失效时间
//         */
//        private Long expirationTime;
//
//        /**
//         * 间隔时间
//         */
//        private Long intervalTime;
//        /**
//         *
//         */
//        private CaptchaSettingProperties setting;
    }
}
