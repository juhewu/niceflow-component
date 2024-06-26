package com.niceflow.component.mongo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niceflow.component.mongo.auditing.DocumentBeforeSaveAuditingHandler;
import com.niceflow.component.mongo.core.MongoTemplatePlus;
import com.niceflow.component.mongo.translate.TableTranslateHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * 数据库配置
 *
 * @author duanjw
 * @date 2023/4/10
 */
@Configuration
@AutoConfigureAfter({MongoDataAutoConfiguration.class})
public class MongoDbAutoConfig {

    /**
     * 扩展 mongoTemplate
     *
     * @param mongoDatabaseFactory mongodb factory
     * @return mongoTemplatePlus
     */
    @Bean
    @ConditionalOnMissingBean
    public MongoTemplatePlus mongoTemplatePlus(MongoDatabaseFactory mongoDatabaseFactory, ObjectMapper objectMapper) {
        MongoTemplatePlus mongoTemplate = new MongoTemplatePlus(mongoDatabaseFactory, objectMapper);
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoMapping.afterPropertiesSet();
        return mongoTemplate;
    }

    /**
     * 新增数据前设置审计信息
     *
     * @return
     */
    @Bean
    public DocumentBeforeSaveAuditingHandler documentBeforeSaveAuditingHandler() {
        return new DocumentBeforeSaveAuditingHandler();
    }

    @Bean
    public TableTranslateHandler tableTranslateHandler(@Qualifier("mongoTemplate") MongoTemplate mongoTemplate) {
        return new TableTranslateHandler(mongoTemplate);
    }
}
