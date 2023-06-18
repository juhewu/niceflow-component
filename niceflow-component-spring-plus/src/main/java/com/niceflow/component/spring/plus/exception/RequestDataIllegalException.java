package com.niceflow.component.spring.plus.exception;

import com.niceflow.component.common.exception.ErrorCode;
import com.niceflow.component.spring.plus.enums.CommonErrorCodeEnum;

/**
 * 请求数据不合法
 *
 * @author duanjw
 * @date 2023/3/8
 */
public class RequestDataIllegalException extends BusinessException {

    /**
     * 请求数据不合法
     */
    public RequestDataIllegalException() {
        super(CommonErrorCodeEnum.REQUEST_DATA_ILLEGAL);
    }

    /**
     * 请求数据不合法
     */
    public RequestDataIllegalException(Object[] args) {
        super(CommonErrorCodeEnum.REQUEST_DATA_ILLEGAL_WITH_PARAM, args);
    }

    /**
     * 请求数据不合法
     *
     * @param errorCode 异常枚举
     */
    public RequestDataIllegalException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 请求数据不合法
     *
     * @param errorCode 异常枚举
     * @param args 异常中的变量
     */
    public RequestDataIllegalException(ErrorCode errorCode, Object[] args) {
        super(errorCode, args);
    }
}
