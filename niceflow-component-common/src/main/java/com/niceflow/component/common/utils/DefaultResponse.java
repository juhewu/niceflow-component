package com.niceflow.component.common.utils;

/**
 * @author duanjw
 * @date 2024/1/24
 */
public class DefaultResponse<T> extends AbstractRefResponse {
    private T value;

    public static <T> DefaultResponse<T> ok(T value) {
        return ok(value, null);
    }

    public static <T> DefaultResponse<T> ok(T value, Object ref) {
        DefaultResponse<T> response = new DefaultResponse<>();
        response.setValue(value);
        response.setRef(ref);
        return response;
    }

    public static DefaultResponse<?> ok() {
        return null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
