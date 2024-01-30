package com.niceflow.component.mongo.translate;

import cn.hutool.core.util.StrUtil;
import com.niceflow.component.translate.annotation.Translate;
import com.niceflow.component.translate.handler.TranslateHandler;
import com.niceflow.component.translate.handler.TranslateHolder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author duanjw
 * @date 2024/1/26
 */
public class TableTranslateHandler implements TranslateHandler {

    private final MongoTemplate mongoTemplate;

    public TableTranslateHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Map<String, Object> execute(List<TranslateHolder> translateHolderList) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Holder> holderHashMap = new HashMap<>();
        for (TranslateHolder translateHolder : translateHolderList) {
            String refTableIdName = translateHolder.getTranslate().refTableIdName();
            String refTableName = translateHolder.getTranslate().refTableName();
            String[] refFiledNames = translateHolder.getTranslate().refFiledNames();
            String refName = StrUtil.firstNonBlank(translateHolder.getTranslate().refName(), StrUtil.toCamelCase(refTableName) + "s");

            if (Objects.isNull(refFiledNames)) {
                continue;
            }
            String key = refTableName + "-" + refTableIdName + "-" + refName;
            Holder holder = holderHashMap.get(key);
            if (Objects.isNull(holder)) {
                holder = new Holder();
                holder.setValues(translateHolder.getValues());
                holder.setRefFiledNames(new HashSet<>(Arrays.asList(refFiledNames)));
                holderHashMap.put(key, holder);
            } else {
                Set<String> refFiledNameSets = holder.getRefFiledNames();
                refFiledNameSets.addAll(Arrays.asList(refFiledNames));
                ArrayList<Object> objects = new ArrayList<>();
                objects.addAll(translateHolder.getValues());
                objects.addAll(holder.getValues());
                holder.setValues(objects);
                holder.setRefFiledNames(refFiledNameSets);
                holderHashMap.put(key, holder);
            }


        }
        holderHashMap.forEach((key, value) -> {
            String[] split = key.split("-");
            String refTableName = split[0];
            String refTableIdName = split[1];
            String refName = split[2];
            List<Object> values = value.getValues().stream().distinct().collect(Collectors.toList());
            Set<String> refFiledNames = value.getRefFiledNames();
            Query query = new Query(Criteria.where(refTableIdName).in(values));
            query.fields().include(refFiledNames.toArray(new String[]{})).include(refTableIdName);

            Optional.of(mongoTemplate.find(query, Map.class, refTableName).stream()
                    .peek(document -> {
                        document.put("id", ((ObjectId) document.get("_id")).toString());
                        document.remove("_id");
                    })
                    .collect(Collectors.toList())).filter(x -> !x.isEmpty()).ifPresent(xx -> {
                result.put(refName, xx);
            });
        });
        return result;
    }

    @Override
    public boolean isSupport(Translate.Mode mode) {
        return Objects.equals(mode, Translate.Mode.TABLE);
    }

    @Data
    static class Holder {
        private Set<String> refFiledNames;
        private List<Object> values;
    }
}
