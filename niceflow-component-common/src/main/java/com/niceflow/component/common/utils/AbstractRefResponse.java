package com.niceflow.component.common.utils;

/**
 * @author duanjw
 * @date 2024/1/26
 */
public abstract class AbstractRefResponse implements RefResponse {
    private Object ref;

    @Override
    public Object getRef() {
        return ref;
    }

    public void setRef(Object ref) {
        this.ref = ref;
    }
}
