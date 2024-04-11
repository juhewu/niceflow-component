package com.niceflow.component.security.resource.user;

import com.niceflow.component.common.user.UserContext;
import com.niceflow.component.common.user.UserContextProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * security 用户信息提供值
 *
 * @author duanjw
 * @date 2023/6/21
 */
public class SecurityUserContextProvider implements UserContextProvider {

    @Override
    public UserContext getUserContext() {
        Authentication authentication = getAuthentication();
        if (Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        if (authentication.getPrincipal() instanceof SecurityUserContext) {
            return converter((SecurityUserContext) authentication.getPrincipal());
        }
        // todo UserDetailResponse
        return null;
    }


    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 转换
     *
     * @param securityUserContext security 用户信息
     * @return
     */
    private UserContext converter(SecurityUserContext securityUserContext) {
        UserContext userContext = new UserContext();
        userContext.setId(securityUserContext.getId());
        userContext.setMemberId(securityUserContext.getMemberId());
        userContext.setDepartmentId(securityUserContext.getDepartmentId());
        userContext.setPassId(securityUserContext.getPassId());
        userContext.setSystemId(securityUserContext.getSystemId());
        userContext.setTenantId(securityUserContext.getTenantId());
        userContext.setCreatedByTenantId(securityUserContext.getCreatedByTenantId());
        userContext.setName(securityUserContext.getName());
        userContext.setAppId(securityUserContext.getAppId());
        return userContext;
    }
}
