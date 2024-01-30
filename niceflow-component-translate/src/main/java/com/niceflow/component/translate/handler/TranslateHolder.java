package com.niceflow.component.translate.handler;

import com.niceflow.component.translate.annotation.Translate;

import java.util.List;

/**
 * @author duanjw
 * @date 2024/1/26
 */
public class TranslateHolder {
    private Translate translate;

    private List<Object> values;

    public Translate.Mode getTranslateMode() {
        return translate.mode();
    }

    public Translate getTranslate() {
        return translate;
    }

    public void setTranslate(Translate translate) {
        this.translate = translate;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }
}
