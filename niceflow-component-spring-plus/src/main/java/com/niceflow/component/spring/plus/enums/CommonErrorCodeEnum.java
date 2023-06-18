package com.niceflow.component.spring.plus.enums;


import com.niceflow.component.common.exception.ErrorCode;

/**
 * 公共错误码
 *
 * @author duanjw
 * @date 2023/3/8
 */
public enum CommonErrorCodeEnum implements ErrorCode {
    /**
     * 请求数据非法
     */
    REQUEST_DATA_ILLEGAL("request.data.illegal"),
    REQUEST_DATA_ILLEGAL_WITH_PARAM("request.data.illegal.param"),
    /**
     * 参数的值必须是空
     */
    ARGS_VALUE_IS_NULL("args.value.null"),
    /**
     * 参数的值不能是空
     */
    ARGS_VALUE_IS_NOT_NULL("args.value.not.null"),
    /**
     * 参数的值必须是 true
     */
    ARGS_VALUE_IS_TRUE("args.value.not.null"),
    ARGS_VALUE_IS_FALSE("args.value.not.null"),
    ARGS_VALUE_HAS_LENGTH("args.value.not.null"),
    ARGS_VALUE_HAS_TEXT("args.value.not.null"),
    ARGS_VALUE_DOES_NOT_CONTAIN("args.value.not.null"),
    ARGS_VALUE_NOT_EMPTY("args.value.not.null"),
    ARGS_VALUE_MUST_EQUALS("args.value.not.null"),
    ARGS_VALUE_MUST_NOT_EQUALS("args.value.not.null"),
    ARGS_VALUE_NO_NULL_ELEMENTS("args.value.not.null");

    /**
     * 国际化消息，对应错误文件.properties 中的key
     */
    private final String messageKey;
    /**
     * 错误码，对应响应数据的 code
     */
    private Integer errorCode;

    CommonErrorCodeEnum(String messageKey) {
        this.messageKey = messageKey;
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    @Override
    public Integer getErrorCode() {
        return this.errorCode;
    }

    /**
     * 获取国际化消息 key
     *
     * @return 国际化消息 key
     */
    @Override
    public String getMessageKey() {
        return this.messageKey;
    }
}
