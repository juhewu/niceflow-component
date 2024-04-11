package com.niceflow.component.common.user;

import lombok.Data;

/**
 * 用户信息
 *
 * @author duanjw
 * @date 2023/6/21
 */
@Data
public class UserContext {

    /**
     * id
     */
    private String id;

    /**
     * 通行证 id
     */
    private String passId;

    /**
     * 租户 id
     */
    private String tenantId;

    /**
     * 由某租户创建
     */
    private String createdByTenantId;

    /**
     * 成员 id
     */
    private String memberId;

    /**
     * 部门 id
     */
    private String departmentId;

    /**
     * 系统 id
     */
    private String systemId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 应用 id
     */
    private String appId;
}
