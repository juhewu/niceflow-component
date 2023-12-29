package com.niceflow.component.security.resource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 许可请求，不需要鉴权
 *
 * @author duanjw
 * @date 2023/8/6
 */
@Data
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver")
public class PermitRequestProperties {

    private Map<String, List<String>> permitRequests;
    private String test;

    public List<AntPathRequestMatcher> getPermitRequestAntPathRequestMatchers() {
        List<AntPathRequestMatcher> antPathRequestMatchers = new ArrayList<>();
        if (Objects.isNull(this.permitRequests)) {
            return antPathRequestMatchers;
        }

        this.permitRequests.forEach((key, value) -> {
            if (Objects.isNull(value)) {
                return;
            }
            value.forEach(item -> antPathRequestMatchers.add(new AntPathRequestMatcher(item, key.toUpperCase())));
        });
        return antPathRequestMatchers;
    }

}
