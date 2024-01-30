package com.niceflow.component.common.utils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 返回数据
 *
 * @param <T> t
 * @author duanjw
 * @date 2023/3/30
 */
public class R<T> implements Serializable {

    public static final Integer SUCCESS = 0;
    public static final Integer FAIL = 1;
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String msg;
    private T data;

    public R(Throwable e) {
        this.msg = e.getMessage();
        this.code = FAIL;
    }

    public R(T data) {
        this.data = data;
    }

    public R() {
    }

    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> ok() {
        return restResult(SUCCESS, null);
    }

    public static <T> R<DefaultResponse<T>> defaultOk(T data) {
        return ok(DefaultResponse.ok(data));
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, FAIL, null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> R<T> failed(Integer code, String msg) {
        return restResult(code, msg);
    }

    private static <T> R<T> restResult(Integer code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        return apiResult;
    }

    private static <T> R<T> restResult(T data, Integer code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        apiResult.data = data;
        return apiResult;
    }

    @Override
    public String toString() {
        return "R(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.data + ")";
    }

    public Integer getCode() {
        return this.code;
    }

    public R<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public R<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}