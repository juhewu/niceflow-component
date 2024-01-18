package com.niceflow.component.mongo.tenant;

import com.niceflow.component.common.annotation.IgnoreTenant;
import com.niceflow.component.common.base.TenantBaseEntity;
import com.niceflow.component.common.user.UserContext;
import com.niceflow.component.common.user.UserContextUtil;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.*;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class TenantMongoRepositoryFactory extends MongoRepositoryFactory {
    private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private final MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext;

    private final MongoOperations operations;

    public TenantMongoRepositoryFactory(MongoOperations mongoOperations) {
        super(mongoOperations);

        this.operations = mongoOperations;
        this.mappingContext = mongoOperations.getConverter().getMappingContext();
    }

    @Override
    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(QueryLookupStrategy.Key key,
                                                                   QueryMethodEvaluationContextProvider evaluationContextProvider) {
        return Optional.of(new TenantMongoQueryLookupStrategy(operations, evaluationContextProvider, mappingContext));
    }

    class TenantMongoQueryLookupStrategy implements QueryLookupStrategy {
        private final MongoOperations operations;
        private final QueryMethodEvaluationContextProvider evaluationContextProvider;
        MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext;
        private final ExpressionParser expressionParser = new CachingExpressionParser(EXPRESSION_PARSER);

        public TenantMongoQueryLookupStrategy(MongoOperations operations, QueryMethodEvaluationContextProvider evaluationContextProvider, MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {

            this.operations = operations;
            this.evaluationContextProvider = evaluationContextProvider;
            this.mappingContext = mappingContext;
        }

        @Override
        public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, ProjectionFactory factory, NamedQueries namedQueries) {
            MongoQueryMethod queryMethod = new MongoQueryMethod(method, metadata, factory, mappingContext);
            String namedQueryName = queryMethod.getNamedQueryName();

            if (namedQueries.hasQuery(namedQueryName)) {
                String namedQuery = namedQueries.getQuery(namedQueryName);
                return new StringBasedMongoQuery(namedQuery, queryMethod, operations, expressionParser, evaluationContextProvider);
            } else if (queryMethod.hasAnnotatedAggregation()) {
                return new StringBasedAggregation(queryMethod, operations, expressionParser, evaluationContextProvider);
            } else if (queryMethod.hasAnnotatedQuery()) {
                return new StringBasedMongoQuery(queryMethod, operations, expressionParser, evaluationContextProvider);
            }
            if (method.getAnnotation(IgnoreTenant.class) != null) {
                return new PartTreeMongoQuery(queryMethod, operations, expressionParser, evaluationContextProvider);
            }
            return new TenantPartTreeMongoQuery(queryMethod, operations, expressionParser, evaluationContextProvider, TenantBaseEntity.class.isAssignableFrom(metadata.getDomainType()));
        }


        private class TenantPartTreeMongoQuery extends PartTreeMongoQuery {

            private final boolean tenant;

            public TenantPartTreeMongoQuery(MongoQueryMethod method, MongoOperations mongoOperations, ExpressionParser expressionParser, QueryMethodEvaluationContextProvider evaluationContextProvider, boolean tenant) {
                super(method, mongoOperations, expressionParser, evaluationContextProvider);
                this.tenant = tenant;
            }

            @Override
            protected Query createQuery(ConvertingParameterAccessor accessor) {
                Query query = super.createQuery(accessor);
                return withCurrentTenant(query);
            }

            @Override
            protected Query createCountQuery(ConvertingParameterAccessor accessor) {
                Query query = super.createCountQuery(accessor);
                return withCurrentTenant(query);
            }

            private Query withCurrentTenant(Query query) {
                if (tenant && !query.getQueryObject().containsKey("tenantId")) {
                    return Optional.ofNullable(UserContextUtil.getUserContext()).map(UserContext::getTenantId).map(x -> query.addCriteria(Criteria.where("tenantId").is(x))).orElse(query);
                }
                return query;
            }
        }
    }

    class CachingExpressionParser implements ExpressionParser {

        private final ExpressionParser delegate;
        private final Map<String, Expression> cache = new ConcurrentHashMap<>();

        CachingExpressionParser(ExpressionParser delegate) {
            this.delegate = delegate;
        }

        @Override
        public Expression parseExpression(String expressionString) throws ParseException {
            return cache.computeIfAbsent(expressionString, delegate::parseExpression);
        }

        @Override
        public Expression parseExpression(String expressionString, ParserContext context) throws ParseException {
            throw new UnsupportedOperationException("Parsing using ParserContext is not supported");
        }
    }

}
