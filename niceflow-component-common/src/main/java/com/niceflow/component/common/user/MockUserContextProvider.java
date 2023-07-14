package com.niceflow.component.common.user;

/**
 * 模拟用户信息提供者
 *
 * @author duanjw
 * @date 2023/6/21
 */
public class MockUserContextProvider implements UserContextProvider {
    @Override
    public UserContext getUserContext() {
        UserContext userContext = new UserContext();
        userContext.setId("643ff50cfa5e911679bbf2f1");
        userContext.setMemberId("643ff50cfa5e911679bbf2f1");
        userContext.setTenantId("643ff50cfa5e911679bbf2f1");
        userContext.setCreatedByTenantId("643ff50cfa5e911679bbf2f1");
        userContext.setPassId("643ff50cfa5e911679bbf2f1");
        userContext.setSystemId("643ff50cfa5e911679bbf2f3");
        userContext.setName("管理员");
        userContext.setDepartmentId("643ff50cfa5e911679bbf2f1");
        return userContext;
    }
}
