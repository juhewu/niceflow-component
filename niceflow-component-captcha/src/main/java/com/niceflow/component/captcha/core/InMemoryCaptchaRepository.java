package com.niceflow.component.captcha.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author duanjw
 * @date 2023/4/18
 */
public class InMemoryCaptchaRepository implements CaptchaRepository {

    private final Map<String, String> storage = new HashMap<>();

    @Override
    public void save(String key, String value) {
        System.out.println("key：" + key + "，value：" + value);
        storage.put(key, value);
    }

    @Override
    public String get(String key) {
        System.out.println("获取 key：" + key + "，对应的 value：" + storage.get(key));

        if (key.equals("18630070626_login_aaa")) {
            return "1234";
        }
        return storage.get(key);
    }

    @Override
    public void remove(String key) {
        storage.remove(key);
    }
}
