package com.niceflow.component.mongo.tenant;

import com.niceflow.component.common.base.TenantBaseEntity;
import com.niceflow.component.common.user.UserContext;
import com.niceflow.component.common.user.UserContextUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.data.util.StreamUtils;
import org.springframework.data.util.Streamable;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author duanjw
 * @date 2024/1/17
 */
public class TenantSimpleMongoRepository<T, ID> extends SimpleMongoRepository<T, ID> {
    private final MongoOperations mongoOperations;
    private final MongoEntityInformation<T, ID> entityInformation;

    /**
     * Creates a new {@link SimpleMongoRepository} for the given {@link MongoEntityInformation} and {@link MongoTemplate}.
     *
     * @param metadata        must not be {@literal null}.
     * @param mongoOperations must not be {@literal null}.
     */
    public TenantSimpleMongoRepository(MongoEntityInformation metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);

        this.entityInformation = metadata;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Optional<T> findById(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        return Optional.ofNullable(
                mongoOperations.findOne(
                        getIdQuery(id),
                        entityInformation.getJavaType(),
                        entityInformation.getCollectionName()));
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {

        Assert.notNull(example, "Sample must not be null!");

        Query q = new Query(new Criteria().alike(example));
        q.addCriteria(getTenantCriteria());
        return Optional.ofNullable(
                mongoOperations.findOne(q, example.getProbeType(), entityInformation.getCollectionName()));
    }

    @Override
    public <S extends T> long count(Example<S> example) {

        Assert.notNull(example, "Sample must not be null!");

        Query q = new Query(new Criteria().alike(example));
        q.addCriteria(getTenantCriteria());
        return mongoOperations.count(q, example.getProbeType(), entityInformation.getCollectionName());
    }
    @Override
    public <S extends T> boolean exists(Example<S> example) {

        Assert.notNull(example, "Sample must not be null!");

        Query q = new Query(new Criteria().alike(example));
        q.addCriteria(getTenantCriteria());
        return mongoOperations.exists(q, example.getProbeType(), entityInformation.getCollectionName());
    }

    @Override
    public boolean existsById(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        return mongoOperations.exists(
                getIdQuery(id),
                entityInformation.getJavaType(),
                entityInformation.getCollectionName());
    }

    @Override
    public long count() {
        return mongoOperations.count(getTenantQuery(), entityInformation.getCollectionName());
    }

    @Override
    public void deleteById(ID id) {
        Assert.notNull(id, "The given id must not be null!");

        mongoOperations.remove(
                getIdQuery(id),
                entityInformation.getJavaType(),
                entityInformation.getCollectionName());
    }

    @Override
    public void delete(T entity) {

        Assert.notNull(entity, "The given entity must not be null!");

        deleteById(entityInformation.getRequiredId(entity));
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {

        Assert.notNull(entities, "The given Iterable of entities not be null!");

        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        mongoOperations.remove(
                getTenantQuery(),
                entityInformation.getJavaType(),
                entityInformation.getCollectionName());
    }
    @Override
    public List<T> findAllById(Iterable<ID> ids) {

        Assert.notNull(ids, "The given Ids of entities not be null");

        return findAll(
                new Query(
                        new Criteria(entityInformation.getIdAttribute())
                                .in(Streamable.of(ids).stream().collect(StreamUtils.toUnmodifiableList()))));
    }
    @Override
    public List<T> findAll() {
        return findAll(new Query());
    }

    @Override
    public <S extends T> Page<S> findAll(final Example<S> example, Pageable pageable) {

        Assert.notNull(example, "Sample must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");

        Query q = new Query(new Criteria().alike(example)).with(pageable);
        q.addCriteria(getTenantCriteria());
        List<S> list =
                mongoOperations.find(q, example.getProbeType(), entityInformation.getCollectionName());

        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () ->
                        mongoOperations.count(
                                q, example.getProbeType(), entityInformation.getCollectionName()));
    }
    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {

        Assert.notNull(example, "Sample must not be null!");
        Assert.notNull(sort, "Sort must not be null!");

        Query q = new Query(new Criteria().alike(example)).with(sort);
        q.addCriteria(getTenantCriteria());
        return mongoOperations.find(q, example.getProbeType(), entityInformation.getCollectionName());
    }

    private List<T> findAll(@Nullable Query query) {
        if (query == null) {
            return Collections.emptyList();
        }

        query.addCriteria(getTenantCriteria());
        return mongoOperations.find(
                query, entityInformation.getJavaType(), entityInformation.getCollectionName());
    }

    private Query getIdQuery(Object id) {
        return new Query(getIdCriteria(id));
    }

    private Criteria getIdCriteria(Object id) {
        Criteria criteria = where(entityInformation.getIdAttribute()).is(id);
        if(isTenant()) {
            return Optional.ofNullable(UserContextUtil.getUserContext()).map(UserContext::getTenantId).map(x -> where("tenantId").is(x)).orElse(new Criteria());
        }
        return criteria;
    }

    private Criteria getTenantCriteria(){
        if(isTenant()) {
            return Optional.ofNullable(UserContextUtil.getUserContext()).map(UserContext::getTenantId).map(x -> where("tenantId").is(x)).orElse(new Criteria());
        }
        return new Criteria();
    }

    private boolean isTenant(){
        return TenantBaseEntity.class.isAssignableFrom(this.entityInformation.getJavaType());
    }

    private Query getTenantQuery(){
        return new Query(getTenantCriteria());
    }
}
