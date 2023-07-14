package com.niceflow.component.mongo.config;

import com.niceflow.component.mongo.core.MongoTemplatePlus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * 数据库配置
 *
 * @author duanjw
 * @date 2023/4/10
 */
@Configuration
public class MongoDbAutoConfig {

    /**
     * 扩展 mongoTemplate
     *
     * @param mongoDatabaseFactory mongodb factory
     * @return mongoTemplatePlus
     */
    @Bean
    @ConditionalOnMissingBean
    public MongoTemplatePlus mongoTemplatePlus(MongoDatabaseFactory mongoDatabaseFactory) {
        MongoTemplatePlus mongoTemplate = new MongoTemplatePlus(mongoDatabaseFactory);
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoMapping.afterPropertiesSet();
        return mongoTemplate;
    }
}
