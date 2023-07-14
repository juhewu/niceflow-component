package com.niceflow.component.common.condition;

import cn.hutool.json.JSONObject;

import java.util.*;

/**
 * mongodb 条件转换
 *
 * @author duanjw
 * @date 2023/3/8
 */
public class MongoConditionParse implements ConditionParse {

    private static final String EMPTY_RESULT = "{}";
    /**
     * todo not nor 未实现
     */
    private static final List<Integer> LOGICAL_LIST = Arrays
            .asList(LogicEnum.AND.getId(), LogicEnum.OR.getId(), LogicEnum.NOT.getId(), LogicEnum.NOR.getId());

    @Override
    public String parse(Condition condition) {
        if (condition == null) {
            return EMPTY_RESULT;
        }
        Map<String, Object> convert = convert(condition);

        return new JSONObject(convert).toString();
    }

    private Map<String, Object> convert(Condition condition) {
        List<Condition> conditions = condition.getConditions();
        List<Map<String, Object>> results = new ArrayList<>();
        for (Condition item : conditions) {
            if (null != item.getConditions() && !item.getConditions().isEmpty()) {
                results.add(convert(item));
            } else if (!LOGICAL_LIST.contains(item.getLogic())) {
                results.add(convertItem(item));
            }
        }
        if (results.isEmpty()) {
            return convertItem(condition);
        }
        return Collections.singletonMap("$" + LogicEnum.get(condition.getLogic()).getValue(), results);
    }

    private Map<String, Object> convertItem(Condition condition) {
        String column = condition.getKey();
        Integer operator = condition.getLogic();
        Object value = condition.getValue();
        Map<String, Object> filterMap = new HashMap<>(16);
        // 无操作符号，当 = 处理
        if (operator == null) {
            filterMap.put(condition.getKey(), value);
        } else { // 包含操作符号 todo 时间是两段，未处理
            filterMap.put(column, Collections.singletonMap("$" + LogicEnum.get(condition.getLogic()).getValue(), value));
        }
        return filterMap;
    }
}
