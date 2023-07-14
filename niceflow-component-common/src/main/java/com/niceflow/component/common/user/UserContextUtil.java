package com.niceflow.component.common.user;


import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * @author duanjw
 * @date 2023/6/21
 */
@UtilityClass
public class UserContextUtil {
    private static UserContextProvider userContextProvider = new MockUserContextProvider();

    public static void setUserContextProvider(@NonNull UserContextProvider provider) {
        userContextProvider = provider;
    }

    public static UserContext getUserContext() {
        if (userContextProvider == null) {
            throw new IllegalStateException("UserInfoProvider 不能为空");
        }
        return userContextProvider.getUserContext();
    }

    public static String getUserId() {
        return getUserContext().getId();
    }

    public static String getMemberId() {
        return getUserContext().getMemberId();
    }

    public static String getTenantId() {
        return getUserContext().getTenantId();
    }
    public static String getPassId() {
        return getUserContext().getPassId();
    }
}
