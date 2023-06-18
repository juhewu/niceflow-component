package com.niceflow.component.security.resource.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 资源服务器配置
 *
 * @author duanjw
 * @date 2023/5/7
 */
@EnableConfigurationProperties(OAuth2ResourceServerProperties.class)
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class ResourceServerConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.anyRequest().authenticated()
                )
                .oauth2ResourceServer().opaqueToken();
        return http.build();
    }


    @Bean
    public OpaqueTokenIntrospector introspector(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        return new CustomAuthoritiesOpaqueTokenIntrospector(oAuth2ResourceServerProperties.getOpaquetoken());
    }
}
