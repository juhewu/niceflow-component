package com.niceflow.component.translate.handler;

import com.niceflow.component.translate.annotation.Translate;

import java.util.List;
import java.util.Map;

/**
 * @author duanjw
 * @date 2024/1/26
 */
public interface TranslateHandler {
    Map<String, Object> execute(List<TranslateHolder> translateHolderList);

    boolean isSupport(Translate.Mode mode);
}
