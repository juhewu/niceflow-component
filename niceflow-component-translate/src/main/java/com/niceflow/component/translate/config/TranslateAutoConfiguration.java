package com.niceflow.component.translate.config;

import com.niceflow.component.translate.TranslateResponseBodyAdvice;
import com.niceflow.component.translate.handler.HttpTranslateHandler;
import com.niceflow.component.translate.handler.TranslateHandler;
import com.niceflow.component.translate.handler.TranslateHandlerManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author duanjw
 * @date 2024/1/27
 */
@Configuration
public class TranslateAutoConfiguration {

    @Bean
    public HttpTranslateHandler httpTranslateHandler() {
        return new HttpTranslateHandler();
    }

    @Bean
    @ConditionalOnBean(TranslateHandler.class)
    public TranslateHandlerManager translateHandlerManager(List<TranslateHandler> translateHandlers) {
        return new TranslateHandlerManager(translateHandlers);
    }
    @Bean
    @ConditionalOnBean(TranslateHandlerManager.class)
    public TranslateResponseBodyAdvice translateResponseBodyAdvice(TranslateHandlerManager translateHandlerManager) {
        return new TranslateResponseBodyAdvice(translateHandlerManager);
    }
}
