package com.niceflow.component.common.utils;

import com.niceflow.component.common.page.PageResponse;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

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
    private Data data;

    public R(Throwable e) {
        this.msg = e.getMessage();
        this.code = FAIL;
    }

    public R(T data) {
        this.setValue(data);
    }

    public R() {
    }

    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.setValue(data);
    }

    public static <T> R<T> ok() {
        return restResult(SUCCESS, null);
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
        apiResult.setValue(data);
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

    public Data getData() {
        return this.data;
    }

    public R<T> setValue(T value) {
        this.data = new Data();
        if (value instanceof PageResponse) {
            this.data.setValue(((PageResponse<?>) value).getRecords());
            this.data.setTotal(((PageResponse<?>) value).getTotal());
            this.data.setPageIndex(((PageResponse<?>) value).getPageIndex());
            this.data.setPageSize(((PageResponse<?>) value).getPageSize());
        } else {
            this.data.value = value;
        }
        return this;
    }

    /**
     * 关联的数据
     *
     * @param ref 关联的数据
     * @return 当前对象
     */
    public R<T> setRef(Map<String, Object> ref) {
        this.data.ref = ref;
        return this;
    }

    /**
     * 数据
     */
    @lombok.Data
    static class Data implements Serializable {

        private Object value;
        private Map<String, Object> ref;

        private Long total;
        private Integer pageIndex;
        private Integer pageSize;

        public void setTotal(Long total) {
            this.total = total;
        }

        public void setPageIndex(Integer pageIndex) {
            this.pageIndex = pageIndex;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }
    }
}