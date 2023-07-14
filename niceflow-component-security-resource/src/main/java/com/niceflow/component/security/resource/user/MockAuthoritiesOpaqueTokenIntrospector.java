package com.niceflow.component.security.resource.user;

import com.niceflow.component.security.resource.user.SecurityUserContext;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.util.HashMap;

public class MockAuthoritiesOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    @SneakyThrows
    public OAuth2AuthenticatedPrincipal introspect(String token) {

        SecurityUserContext securityUserContext = new SecurityUserContext();
        securityUserContext.setId("userId-1");
        securityUserContext.setMemberId("memberId-1");
        securityUserContext.setTenantId("tenantId-1");
        securityUserContext.setCreatedByTenantId("createdByTenantId-1");
        securityUserContext.setPassId("passId-1");
        securityUserContext.setSystemId("systemId-1");
        securityUserContext.setDepartmentId("departmentId-1");
        securityUserContext.setAttributes(new HashMap<>());
        return securityUserContext;
    }
}