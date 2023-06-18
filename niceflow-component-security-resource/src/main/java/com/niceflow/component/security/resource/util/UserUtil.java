package com.niceflow.component.security.resource.util;

import com.niceflow.component.security.resource.config.UserContext;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户信息
 *
 * @author duanjw
 * @date 2023/6/18
 */
@UtilityClass
public class UserUtil {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static UserContext getUser() {
        Authentication authentication = getAuthentication();
        if (authentication.getPrincipal() instanceof UserContext) {
            return (UserContext) authentication.getPrincipal();
        }
        throw new RuntimeException();
    }

    public static String getUserId() {
        return getUser().getId();
    }

    public static String getTenantId() {
        return getUser().getTenantId();
    }

    public static String getMemberId() {
        return getUser().getMemberId();
    }

    public static String getPassId() {
        return getUser().getPassId();
    }
}
