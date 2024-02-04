package com.niceflow.component.mongo.auditing;

import com.niceflow.component.common.base.BaseEntity;
import com.niceflow.component.common.base.TenantBaseEntity;
import com.niceflow.component.common.user.UserContext;
import com.niceflow.component.common.user.UserContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * document 持久化之前，设置审计信息
 * <p>
 * 1. 当前持久化的对象不属于  BaseEntity，不会设置审计信息
 * 2. 用户未登录不会设置审计信息
 * 3. 设置的审计信息包括：createdBy、createdAt、tenantId
 *
 * @author duanjw
 * @date 2023/6/22
 */
@Slf4j
public class DocumentBeforeSaveAuditingHandler implements ApplicationListener<BeforeSaveEvent<?>> {

    @Override
    public void onApplicationEvent(BeforeSaveEvent<?> event) {
        Object source = event.getSource();
        Document document = event.getDocument();
        if (Objects.isNull(document) || !(source instanceof BaseEntity)) {
            return;
        }
        document.putIfAbsent("createdAt", LocalDateTime.now());

        UserContext userContext = null;
        try {
            userContext = UserContextUtil.getUserContext();
        } catch (Exception e) {
            log.warn("获取用户信息失败，不填充审计信息");
        }

        if (Objects.isNull(userContext)) {
            log.warn("用户未登录，不填充审计信息");
            return;
        }

        log.debug("开始填充审计信息，所属对象：{}", source.getClass().getName());
        document.putIfAbsent("createdBy", userContext.getMemberId());
        if(source instanceof TenantBaseEntity) {
            document.putIfAbsent("tenantId", userContext.getTenantId());
        }
    }
}