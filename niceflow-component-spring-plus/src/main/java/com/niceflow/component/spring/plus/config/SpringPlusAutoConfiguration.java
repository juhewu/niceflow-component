package com.niceflow.component.spring.plus.config;

import com.niceflow.component.spring.plus.util.SpringApplicationContextUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author duanjw
 * @date 2023/5/22
 */
@Configuration
public class SpringPlusAutoConfiguration {

    @Bean
    public SpringApplicationContextUtil springApplicationContextUtil(){
        return new SpringApplicationContextUtil();
    }
}
