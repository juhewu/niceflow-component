package com.niceflow.component.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略租户
 * <p>
 * 不自动添加租户条件，适用于 mongodb 的 repository
 *
 * @author duanjw
 * @date 2023/1/8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IgnoreTenant {
}
