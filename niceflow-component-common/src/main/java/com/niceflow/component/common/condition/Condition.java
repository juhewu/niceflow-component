package com.niceflow.component.common.condition;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 条件
 *
 * @author duanjw
 * @date 2023/2/14
 */
@Data
public class Condition {

    /**
     * 字段名称
     */
    private String key;
    /**
     * 逻辑操作符
     */
    private Integer logic;
    /**
     * 值
     */
    private Object value;
    /**
     * 子条件
     */
    private List<Condition> conditions = new ArrayList<>();

    public Condition() {
    }

    public Condition(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Condition(String key, Integer logic, Object value) {
        this.key = key;
        this.logic = logic;
        this.value = value;
    }

    public static Condition or(Condition... conditions) {
        Condition condition = new Condition();
        condition.setLogic(LogicEnum.OR.getId());
        condition.setConditions(Arrays.asList(conditions));
        return condition;
    }

    public static Condition and(Condition... conditions) {
        Condition condition = new Condition();
        condition.setLogic(LogicEnum.AND.getId());
        condition.setConditions(Arrays.asList(conditions));
        return condition;
    }

    public String parse() {
        ConditionParse conditionParse = new MongoConditionParse();
        return conditionParse.parse(this);
    }

    public Condition appendAnd(Condition condition) {
        Condition result = new Condition();
        result.setLogic(LogicEnum.AND.getId());
        result.setConditions(Arrays.asList(this, condition));
        return result;
    }
}


