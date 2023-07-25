package com.niceflow.component.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 环境配置
 *
 * @author duanjw
 * @date 2023/7/21
 */
@Data
@ConfigurationProperties(prefix = "niceflow.env")
public class EnvProperties {

    /**
     * 环境地址
     */
    private String host;

    /**
     * 登录环境地址
     */
    private String loginHost;

    /**
     * niceflow 环境地址
     */
    private String niceflowHost;

    /**
     * 短链接环境地址
     */
    private String shortUrlHost;
}