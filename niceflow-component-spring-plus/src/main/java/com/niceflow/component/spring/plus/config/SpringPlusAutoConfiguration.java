package com.niceflow.component.spring.plus.config;

import com.niceflow.component.spring.plus.exception.GlobalExceptionHandler;
import com.niceflow.component.spring.plus.feign.FeignHeaderWrapInterceptor;
import com.niceflow.component.spring.plus.response.WrapResponseBodyAdvice;
import com.niceflow.component.spring.plus.util.SpringApplicationContextUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author duanjw
 * @date 2023/5/22
 */
@Configuration
public class SpringPlusAutoConfiguration {

    @Bean
    public SpringApplicationContextUtil springApplicationContextUtil() {
        return new SpringApplicationContextUtil();
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public WrapResponseBodyAdvice wrapResponseBodyAdvice() {
        return new WrapResponseBodyAdvice();
    }


    @Configuration
    @ConditionalOnClass(name = "feign.RequestInterceptor")
    public static class CustomFeignConfig {
        @Bean
        public FeignHeaderWrapInterceptor feignHeaderWrapInterceptor() {
            return new FeignHeaderWrapInterceptor();
        }
    }
}
