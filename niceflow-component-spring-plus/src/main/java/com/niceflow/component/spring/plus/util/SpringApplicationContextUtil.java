package com.niceflow.component.spring.plus.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取 ApplicationContext
 *
 * @author duanjw
 * @date 2023/2/16
 */
public class SpringApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        if (applicationContext == null) {
            setApplicationContextStatic(context);
        }
    }

    public static void setApplicationContextStatic(ApplicationContext context) {
        applicationContext = context;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}