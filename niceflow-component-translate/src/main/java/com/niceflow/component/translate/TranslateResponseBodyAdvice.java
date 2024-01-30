package com.niceflow.component.translate;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.niceflow.component.common.utils.RefResponse;
import com.niceflow.component.translate.annotation.IgnoreTranslate;
import com.niceflow.component.translate.annotation.Translate;
import com.niceflow.component.translate.handler.TranslateHandler;
import com.niceflow.component.translate.handler.TranslateHandlerManager;
import com.niceflow.component.translate.handler.TranslateHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author duanjw
 * @date 2024/1/24
 */
@Slf4j
@ControllerAdvice
public class TranslateResponseBodyAdvice implements ResponseBodyAdvice, Ordered {
    private final TranslateHandlerManager translateHandlerManager;

    public TranslateResponseBodyAdvice(TranslateHandlerManager translateHandlerManager) {
        this.translateHandlerManager = translateHandlerManager;
    }


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (returnType.hasMethodAnnotation(IgnoreTranslate.class)) {
            log.debug("包含注解：{}，返回数据不翻译", IgnoreTranslate.class.getSimpleName());
            return body;
        }

        if (body instanceof RefResponse refResponse) {
            List<TranslateHolder> translateHolder = new ArrayList<>();
            processObject(body, translateHolder);

            if(!translateHolder.isEmpty()) {
                Optional.ofNullable(translateHandlerManager.execute(translateHolder)).ifPresent(refResponse::setRef);
            }
            return refResponse;
        }
        return body;
    }

    public void processObject(Object object, List<TranslateHolder> translateHolder) {
        if (object == null) {
            return;
        }

        // 如果是集合类型，递归处理每个元素
        if (object instanceof Collection) {
            for (Object item : (Collection<?>) object) {
                processObject(item, translateHolder);
            }
        } else if (object instanceof Map) {
            // 如果是Map类型，递归处理每个值
            Map<?, ?> map = (Map<?, ?>) object;
            for (Object value : map.values()) {
                processObject(value, translateHolder);
            }
        } else {
            // 遍历对象的字段
            Field[] fields = ReflectUtil.getFields(object.getClass());
            for (Field field : fields) {
                processField(object, field, translateHolder);
            }
        }
    }

    private void processField(Object object, Field field, List<TranslateHolder> translateHolder) {
        // 检查字段上是否有@Translate注解
        Annotation translateAnnotation = field.getAnnotation(Translate.class);

        if (translateAnnotation != null) {
            // 如果有@Translate注解，获取注解的值
            Translate translate = (Translate) translateAnnotation;
            try {
                // 获取字段的值
                field.setAccessible(true);
                Object fieldValue = field.get(object);

                if (Objects.isNull(fieldValue)) {
                    return;
                }
                TranslateHolder values = new TranslateHolder();
                values.setTranslate(translate);

                if (fieldValue instanceof Collection<?> collection) {
                    values.setValues(new ArrayList<>(collection));
                } else {
                    values.setValues(List.of(fieldValue));
                }
                translateHolder.add(values);

                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 如果字段是集合类型，进一步处理每个元素
        if (Collection.class.isAssignableFrom(field.getType())) {
            processCollectionField(object, field, translateHolder);
            return;
        }
        if (ClassUtil.isSimpleTypeOrArray(field.getType())) {
            return;
        }
        Object o;
        try {
            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                return;
            }
            field.setAccessible(true);
            o = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        processObject(o, translateHolder);
    }

    private void processCollectionField(Object object, Field field, List<TranslateHolder> translateHolder) {
        try {
            field.setAccessible(true);
            // 通过反射获取集合字段的值
            Collection<?> collectionValue = (Collection<?>) field.get(object);
            if (collectionValue != null) {
                // 递归处理集合中的每个元素
                for (Object item : collectionValue) {
                    if (ClassUtil.isSimpleValueType(item.getClass())) {
                        return;
                    }
                    processObject(item, translateHolder);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE - 1;
    }
}
