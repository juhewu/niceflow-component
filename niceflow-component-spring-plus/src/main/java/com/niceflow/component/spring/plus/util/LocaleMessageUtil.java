package com.niceflow.component.spring.plus.util;

import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 消息国际化工具类
 *
 * @author duanjw
 * @date 2023/2/16
 */
@UtilityClass
public class LocaleMessageUtil {

    /**
     * 根据 code 获取国际化消息
     *
     * @param code code，国际化资源文件中的 key
     * @param args 国际化消息中变量替换的参数
     * @return 某个国际化消息
     */
    public static String getMessage(String code, Object[] args) {
        MessageSource bean = SpringApplicationContextUtil.getBean(MessageSource.class);
        return bean.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}