package com.niceflow.component.security.resource.config;

import com.niceflow.component.common.user.UserContextProvider;
import com.niceflow.component.common.user.UserContextUtil;
import com.niceflow.component.security.resource.response.AuthenticationEntryPointResponseJson;
import com.niceflow.component.security.resource.user.CustomAuthoritiesOpaqueTokenIntrospector;
import com.niceflow.component.security.resource.user.MockAuthoritiesOpaqueTokenIntrospector;
import com.niceflow.component.security.resource.user.SecurityUserContextProvider;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 资源服务器配置
 *
 * @author duanjw
 * @date 2023/5/7
 */
@EnableConfigurationProperties({OAuth2ResourceServerProperties.class, PermitRequestProperties.class})
@Configuration(proxyBeanMethods = false)
public class ResourceServerAutoConfiguration {

    @Bean
    public ResourceServerWebSecurityConfig resourceServerWebSecurityConfig(PermitRequestProperties permitRequestProperties) {
        return new ResourceServerWebSecurityConfig(permitRequestProperties);
    }

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

    @EnableWebSecurity
    @AllArgsConstructor
    @ConditionalOnMissingClass("org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService")
    public static class ResourceServerWebSecurityConfig {
        private final PermitRequestProperties permitRequestProperties;

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            // 除去忽略鉴权的 url，其它 url 都需要鉴权
            http.authorizeHttpRequests(authorize -> authorize.requestMatchers(permitRequestProperties.getPermitRequestAntPathRequestMatchers().toArray(new AntPathRequestMatcher[0])).permitAll()
                    .anyRequest().authenticated());

            http
                    .csrf().disable()
                    .oauth2ResourceServer(server ->
                            server
                                    .authenticationEntryPoint(new AuthenticationEntryPointResponseJson())
                                    .opaqueToken());
            return http.build();
        }
    }
}
