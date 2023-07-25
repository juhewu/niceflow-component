package com.niceflow.component.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author duanjw
 * @date 2023/7/21
 */
@Import(EnvProperties.class)
@Configuration
public class ConfigAutoConfiguration {

}
