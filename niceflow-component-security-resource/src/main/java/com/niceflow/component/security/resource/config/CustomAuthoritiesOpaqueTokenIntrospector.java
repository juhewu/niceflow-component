package com.niceflow.component.security.resource.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.util.Map;

public class CustomAuthoritiesOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
    private final OpaqueTokenIntrospector delegate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthoritiesOpaqueTokenIntrospector(OAuth2ResourceServerProperties.Opaquetoken opaquetoken) {
        delegate = new NimbusOpaqueTokenIntrospector(opaquetoken.getIntrospectionUri(), opaquetoken.getClientId(), opaquetoken.getClientSecret());
    }

    @SneakyThrows
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2AuthenticatedPrincipal principal = this.delegate.introspect(token);
        Map<String, Object> attributes = principal.getAttributes();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UserContext userContext = objectMapper.readValue(attributes.get("userInfo").toString(), UserContext.class);
        userContext.setAttributes(attributes);
        return userContext;
    }
}