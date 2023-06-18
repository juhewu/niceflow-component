package com.niceflow.component.spring.plus.util;

import lombok.Data;
import lombok.experimental.UtilityClass;

/**
 * 用户工具类
 * <p>
 * 获取当前登录用户的信息
 *
 * @author duanjw
 * @date 2023/3/8
 */
@UtilityClass
public class UserUtil {

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    public static User getUser() {
        return new User();
    }

    /**
     * 获取当前登录用户 id
     *
     * @return 当前登录用户 id
     */
    public static String getId() {
        return getUser().getId();
    }

    /**
     * 获取当前登录用户租户 id
     *
     * @return 当前登录用户租户 id
     */
    public static String getTenantId() {
        return getUser().getTenantId();
    }

    /**
     * 获取当前登录用户成员 id
     *
     * @return 当前登录用户成员 id
     */
    public static String getMemberId() {
        return getUser().getMemberId();
    }

    /**
     * 用户信息
     */
    @Data
    public static class User {

        /**
         * id
         */
        private String id;
        /**
         * 成员 id
         */
        private String memberId;
        /**
         * 租户 id
         */
        private String tenantId;
    }
}
