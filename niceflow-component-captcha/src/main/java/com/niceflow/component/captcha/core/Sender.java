package com.niceflow.component.captcha.core;

import com.niceflow.component.common.enums.BaseEnum;
import lombok.Data;

import java.util.Objects;

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
     *
     * @see SendTypeEnum
     */
    private String type;

    /**
     * 内容类型
     *
     * @see SenderContentTypeEnum
     */
    private String contentType;

    /**
     * 内容
     */
    private String content;

    /**
     * 验证码
     */
    private String captcha;

    public enum SendTypeEnum implements BaseEnum<String> {
        EMAIL("email"),
        SMS("sms"),
        WECHAT("wechat"),
        ;
        private final String value;

        SendTypeEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        public static SendTypeEnum getByValue(String value) {
            for (SendTypeEnum sendTypeEnum : values()) {
                if(Objects.equals(sendTypeEnum.getValue(), value)) {
                    return sendTypeEnum;
                }
            }
            return null;
        }
    }

    public enum SenderContentTypeEnum implements BaseEnum<String> {
        TEMPLATE_ID("template_id"),
        TEMPLATE_CODE("template_code"),
        CONTENT("content"),
        ;

        private final String value;

        SenderContentTypeEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }
}
