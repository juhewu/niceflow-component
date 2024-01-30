package com.niceflow.component.translate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 翻译
 *
 * @author duanjw
 * @date 2024/1/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Translate {
    /**
     * 翻译方式
     */
    Mode mode();

    String refName() default "";

    String refTableName() default "";

    String refTableIdName() default "_id";

    String[] refFiledNames() default "name";

    String refServiceName() default "";

    String refPath() default "";

    String refMethod() default "POST";

    String refHost() default "";


    enum Mode {
        TABLE, HTTP, HTTP_BALANCE
    }
}
