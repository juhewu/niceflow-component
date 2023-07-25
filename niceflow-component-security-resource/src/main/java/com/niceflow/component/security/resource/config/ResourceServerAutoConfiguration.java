package com.niceflow.component.security.resource.config;

import com.niceflow.component.common.user.UserContextProvider;
import com.niceflow.component.common.user.UserContextUtil;
import com.niceflow.component.security.resource.user.CustomAuthoritiesOpaqueTokenIntrospector;
import com.niceflow.component.security.resource.user.MockAuthoritiesOpaqueTokenIntrospector;
import com.niceflow.component.security.resource.user.SecurityUserContextProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * 资源服务器配置
 *
 * @author duanjw
 * @date 2023/5/7
 */
@EnableConfigurationProperties(OAuth2ResourceServerProperties.class)
@Configuration(proxyBeanMethods = false)
public class ResourceServerAutoConfiguration {

    //    @Bean
//    @ConditionalOnMissingClass("org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService")
//    public ResourceServerWebSecurityConfig resourceServerWebSecurityConfig(){
//        return new ResourceServerWebSecurityConfig();
//    }
    @Bean
    @ConditionalOnProperty(value = "spring.security.oauth2.resourceserver.enabled", havingValue = "true")
    public OpaqueTokenIntrospector customAuthoritiesOpaqueTokenIntrospector(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        return new CustomAuthoritiesOpaqueTokenIntrospector(oAuth2ResourceServerProperties.getOpaquetoken());
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.oauth2.resourceserver.enabled", havingValue = "false", matchIfMissing = true)
    public OpaqueTokenIntrospector mockAuthoritiesOpaqueTokenIntrospector() {
        return new MockAuthoritiesOpaqueTokenIntrospector();
    }

    /**
     * 由 security 提供用户信息
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            value = {"spring.security.oauth2.resourceserver.enabled"},
            havingValue = "true"
    )
    public UserContextProvider userContextProvider() {
        SecurityUserContextProvider securityUserContextProvider = new SecurityUserContextProvider();
        UserContextUtil.setUserContextProvider(securityUserContextProvider);
        return securityUserContextProvider;
    }
}
