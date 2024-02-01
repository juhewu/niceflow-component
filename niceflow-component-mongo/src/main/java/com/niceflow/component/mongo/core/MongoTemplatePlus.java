package com.niceflow.component.mongo.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.niceflow.component.common.condition.Condition;
import com.niceflow.component.common.condition.MongoConditionParse;
import com.niceflow.component.common.constants.CommonParamsConstant;
import com.niceflow.component.common.db.DbTemplate;
import com.niceflow.component.common.enums.NumberEnum;
import com.niceflow.component.common.page.DefaultPageResponse;
import com.niceflow.component.common.page.PageQuery;
import com.niceflow.component.common.page.PageRequest;
import com.niceflow.component.common.page.PageResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.CursorPreparer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.util.BsonUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * mongodbTemplate 增强
 *
 * @author duanjw
 */
public class MongoTemplatePlus extends MongoTemplate implements DbTemplate {

    public final ObjectMapper objectMapper;

    public MongoTemplatePlus(MongoDatabaseFactory mongoDbFactory, ObjectMapper objectMapper) {
        super(mongoDbFactory);
        this.objectMapper = objectMapper;

    }

    public MongoTemplatePlus(MongoDatabaseFactory mongoDbFactory, MongoConverter mongoConverter, ObjectMapper objectMapper) {
        super(mongoDbFactory, mongoConverter);
        this.objectMapper = objectMapper;
    }

    public static Update buildUpdate(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;

        }
        Update update = new Update();
        for (Map.Entry<String, Object> entries : paramMap.entrySet()) {
            update.set(entries.getKey(), entries.getValue());
        }
        return update;
    }

    public long count(String collectionName, PageQuery<Condition> pageQuery) {
        Condition condition = pageQuery.getQuery();
        Document filterBson = null;
        // 过滤条件
        if (condition != null) {
            filterBson = Document.parse(condition.parse());
        }
        return count(collectionName, filterBson);
    }

    public long count(String collectionName, Document filterDocument) {
        return super.getCollection(collectionName).countDocuments(filterDocument);
    }

    /**
     * 单条查询
     *
     * @param collectionName
     * @param condition
     * @param showFields
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T findOne(String collectionName, Condition condition, List<String> showFields, Class<T> clazz) {
        String parse = new MongoConditionParse().parse(condition);
        Document filterDocument = Document.parse(parse);
        if (null != showFields && !showFields.isEmpty()) {
            Document filedDocument = new Document();
            showFields.forEach(item -> filedDocument.put(item, 1));
            return findOne(new BasicQuery(filterDocument, filedDocument), clazz, collectionName);
        }
        return findOne(new BasicQuery(filterDocument), clazz, collectionName);
    }

    public <T> PageResponse<T> paginate(Query query, PageRequest pageRequest, Class<T> clazz) {
        long count = count(query, clazz);
        if (count == 0) {
            return new DefaultPageResponse<>(pageRequest.getPageIndex(), pageRequest.getPageSize());
        }
        query.limit(pageRequest.getPageSize())
                .skip((long) (pageRequest.getPageIndex() - 1) * pageRequest
                        .getPageSize());
        return new DefaultPageResponse<>(find(query, clazz), count, pageRequest.getPageIndex(), pageRequest.getPageSize());
    }

    public <T> PageResponse<T> paginate(PageQuery<Condition> pageQuery, Class<T> clazz) {
        String collectionName = getCollectionName(clazz);
        return paginate(collectionName, pageQuery, clazz);
    }

    /**
     * 分页查询
     *
     * @param collectionName
     * @param pageQuery
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> PageResponse<T> paginate(String collectionName, PageQuery<Condition> pageQuery, Class<T> clazz) {
        String parse = new MongoConditionParse().parse(pageQuery.getQuery());
        PageRequest pageRequest = pageQuery.getPageRequest();

        Document filterDocument = Document.parse(parse);
        long count = count(collectionName, filterDocument);
        if (count == 0) {
            return new DefaultPageResponse<>(pageRequest.getPageIndex(), pageRequest.getPageSize());
        }

        List<T> list = super.doFind(collectionName, filterDocument, BsonUtils.EMPTY_DOCUMENT, clazz,
                new PageQueryCursorPreparer(pageQuery));
        return new DefaultPageResponse<>(list, count, pageRequest.getPageIndex(), pageRequest.getPageSize());
    }

    public void update(Object object) {
        Map<String, Object> objectMap = objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {

        });
        objectMap.values().removeIf(Objects::isNull);
        Object id = objectMap.remove(CommonParamsConstant.ID);
        Update update = new Update();
        objectMap.forEach(update::set);
        this.updateFirst(Query.query(Criteria.where(CommonParamsConstant.ID).is(id)), update, object.getClass());

    }

    public Boolean updateById(String collectionName, String id, Map<String, Object> updateMap) {
        Update update = buildUpdate(updateMap);
        Query query = new Query(new Criteria(CommonParamsConstant.UNDERLINE_ID).is(new ObjectId(id)));
        return super.updateFirst(query, update, collectionName).getModifiedCount() > 0;
    }

    static class PageQueryCursorPreparer implements CursorPreparer {

        private final PageQuery<Condition> pageQuery;

        public PageQueryCursorPreparer(PageQuery<Condition> pageQuery) {
            this.pageQuery = pageQuery;
        }

        @Override
        public FindIterable<Document> prepare(FindIterable<Document> iterable) {

            Condition condition = pageQuery.getQuery();
            PageRequest pageRequest = pageQuery.getPageRequest();
            // 分页
            iterable.limit(pageRequest.getPageSize())
                    .skip((pageRequest.getPageIndex() - 1) * pageRequest
                            .getPageSize());

            // 数据设置，过滤、排序、显示部分字段
            List<String> sorts = pageRequest.getSorts();
            List<String> showControls = pageRequest.getShowControls();

            // 查询部分列，1显示列 -1不显示列
            if (showControls != null) {
                Document document = new Document();
                for (String id : showControls) {
                    document.put(id, 1);
                }
                iterable.projection(document);
            }

            // 排序，1正序 -1倒序
            if (!sorts.isEmpty()) {
                Document document = new Document();
                for (int i = 0; i < sorts.size(); i = i + NumberEnum.TWO.getValue()) {
                    document.put(sorts.get(i), Integer.valueOf(sorts.get(i + NumberEnum.ZERO.getValue())));
                }
                iterable.sort(document);
            }

            // 过滤条件
            if (condition != null) {
                iterable.filter(Document.parse(condition.parse()));
            }
            return iterable;
        }
    }
}
