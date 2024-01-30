package com.niceflow.component.translate.handler;

import com.niceflow.component.translate.annotation.Translate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author duanjw
 * @date 2024/1/27
 */
public class TranslateHandlerManager {
    private final List<TranslateHandler> translateHandlers;

    public TranslateHandlerManager(List<TranslateHandler> translateHandlers) {
        this.translateHandlers = translateHandlers;
    }

    public Object execute(List<TranslateHolder> translateHolder) {

        Map<Translate.Mode, List<TranslateHolder>> translateHolderMap = translateHolder.stream()
                .collect(Collectors.groupingBy(TranslateHolder::getTranslateMode));

        Iterator<Map.Entry<Translate.Mode, List<TranslateHolder>>> iterator = translateHolderMap.entrySet().iterator();
        Map<String, Object> result = new HashMap<>();
        while (iterator.hasNext()) {
            Map.Entry<Translate.Mode, List<TranslateHolder>> next = iterator.next();
            for (TranslateHandler translateHandler : translateHandlers) {
                if (translateHandler.isSupport(next.getKey())) {
                    Map<String, Object> execute = translateHandler.execute(next.getValue());
                    result.putAll(execute);
                }
            }
        }
        return result;
    }
}
