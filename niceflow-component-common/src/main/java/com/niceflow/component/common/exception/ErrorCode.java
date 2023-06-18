package com.niceflow.component.common.exception;

/**
 * 错误码
 *
 * @author duanjw
 * @date 2023/3/8
 */
public interface ErrorCode {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    Integer getErrorCode();

    /**
     * 获取国际化消息 key
     *
     * @return 国际化消息 key
     */
    String getMessageKey();
}
