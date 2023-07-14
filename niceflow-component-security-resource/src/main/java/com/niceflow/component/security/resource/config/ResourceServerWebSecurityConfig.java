package com.niceflow.component.security.resource.config;

import com.niceflow.component.security.resource.response.AuthenticationEntryPointResponseJson;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author duanjw
 * @date 2023/7/9
 */
@EnableWebSecurity
public class ResourceServerWebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.anyRequest().authenticated()
                )
                .oauth2ResourceServer(server ->
                        server.authenticationEntryPoint(new AuthenticationEntryPointResponseJson())
                                .opaqueToken());
        return http.build();
    }
}
