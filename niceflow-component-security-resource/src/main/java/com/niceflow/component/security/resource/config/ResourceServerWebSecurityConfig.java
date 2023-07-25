package com.niceflow.component.security.resource.config;

import com.niceflow.component.security.resource.response.AuthenticationEntryPointResponseJson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author duanjw
 * @date 2023/7/9
 */
@Configuration
@EnableWebSecurity
@ConditionalOnMissingClass("org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService")
public class ResourceServerWebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.anyRequest().authenticated()
                )
                .oauth2ResourceServer(server ->
                        server
                                .authenticationEntryPoint(new AuthenticationEntryPointResponseJson())
//                                .withObjectPostProcessor(new ObjectPostProcessor<BearerTokenAuthenticationFilter>() {
//                                    @Override
//                                    public <O extends BearerTokenAuthenticationFilter> O postProcess(O filter) {
////                                        filter.setAuthenticationEntryPoint(new AuthenticationEntryPointResponseJson());
//                                        filter.setAuthenticationFailureHandler((request, response, exception) -> {
////                                            System.out.println("Authentication failed (and is being handled in a custom way)");
////                                            BearerTokenAuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();
////                                            delegate.commence(request, response, exception);
//                                            new AuthenticationEntryPointResponseJson().commence(request, response, exception);
//                                        });
//                                        return filter;
//                                    }
//                                })
                                .opaqueToken());

        return http.build();
    }
}
