package com.niceflow.component.spring.plus.exception;


import com.niceflow.component.common.exception.ErrorCode;
import com.niceflow.component.spring.plus.util.LocaleMessageUtil;

import java.util.Optional;

/**
 * 业务异常
 *
 * @author duanjw
 * @date 2023/2/16
 */
public class BusinessException extends RuntimeException {

    /**
     * 业务错误码，默认是 1
     */
    private final Integer errorCode;

    /**
     * 业务异常
     *
     * @param errorCode 异常枚举
     */
    public BusinessException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    /**
     * 业务异常
     *
     * @param errorCode 异常枚举
     * @param args 异常中的变量
     */
    public BusinessException(ErrorCode errorCode, Object[] args) {
        super(LocaleMessageUtil.getMessage(errorCode.getMessageKey(), args));
        this.errorCode = Optional.ofNullable(errorCode.getErrorCode()).orElse(1);
    }

    /**
     * 获取业务错误码
     *
     * @return 业务错误码
     */
    public Integer getErrorCode() {
        return errorCode;
    }
}
