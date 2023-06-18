package com.niceflow.component.spring.plus.exception;

import com.niceflow.component.common.exception.ErrorCode;

/**
 * 断言异常
 *
 * @author duanjw
 * @date 2023/3/8
 */
public class AssertException extends BusinessException {

    /**
     * 断言异常
     *
     * @param errorCode 异常枚举
     */
    public AssertException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 断言异常
     *
     * @param errorCode 异常枚举
     * @param args 异常中的变量
     */
    public AssertException(ErrorCode errorCode, Object[] args) {
        super(errorCode, args);
    }
}
