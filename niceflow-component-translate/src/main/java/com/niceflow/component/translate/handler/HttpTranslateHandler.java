package com.niceflow.component.translate.handler;

import com.niceflow.component.translate.annotation.Translate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author duanjw
 * @date 2024/1/27
 */
public class HttpTranslateHandler implements TranslateHandler{
    @Override
    public Map<String, Object> execute(List<TranslateHolder> translateHolderList) {
        return null;
    }

    @Override
    public boolean isSupport(Translate.Mode mode) {
        return Objects.equals(Translate.Mode.HTTP, mode);
    }
}
