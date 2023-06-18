package com.niceflow.component.common.base;

import lombok.Getter;
import lombok.Setter;

/**
 * 租户
 *
 * @author duanjw
 * @date 2023/3/8
 */
@Getter
@Setter
public class TenantBaseEntity extends BaseEntity {

    /**
     * 租户 id
     */
    private String tenantId;
}
