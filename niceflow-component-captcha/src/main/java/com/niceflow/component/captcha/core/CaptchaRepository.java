package com.niceflow.component.captcha.core;

/**
 * @author duanjw
 * @date 2023/4/18
 */
public interface CaptchaRepository {
    void save(String key, String value);

    String get(String key);
    void remove(String key);

    default boolean isSupport() {
        return false;
    }
}
