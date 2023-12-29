package com.niceflow.component.common.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 资源释放
 *
 * @author duanjw
 * @date 2023/8/14
 */
@Slf4j
@UtilityClass
public class CloseableUtil {
    public static <T extends AutoCloseable> void close(T resource) {
        if (null == resource) {
            return;
        }
        try {
            resource.close();
        } catch (Exception e) {
            log.error("资源释放异常", e);
        }
    }
}
