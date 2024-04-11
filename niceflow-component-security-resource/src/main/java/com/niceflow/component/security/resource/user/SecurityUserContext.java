package com.niceflow.component.security.resource.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author duanjw
 * @date 2023/6/18
 */
@Data
public class SecurityUserContext implements OAuth2AuthenticatedPrincipal {
    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

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
     * 应用 id
     */
    private String appId;

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 权限
     */
    private List<String> permissions;


    private Map<String, Object> attributes;

    private List<CustomSimpleGrantedAuthority> authorities;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }


    static class CustomSimpleGrantedAuthority implements GrantedAuthority {

        public CustomSimpleGrantedAuthority() {
        }

        public CustomSimpleGrantedAuthority(String authority) {
            this.authority = authority;
        }

        private String authority;

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}
