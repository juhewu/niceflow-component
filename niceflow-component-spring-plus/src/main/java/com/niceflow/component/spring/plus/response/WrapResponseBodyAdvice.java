package com.niceflow.component.spring.plus.response;

import com.niceflow.component.common.utils.R;
import com.niceflow.component.spring.plus.constants.ParamConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 使用 R 包装 response
 *
 * @author duanjw
 * @date 2024/1/24
 */
@Slf4j
@ControllerAdvice
public class WrapResponseBodyAdvice implements ResponseBodyAdvice<Object>, Ordered {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !(R.class.isAssignableFrom(returnType.getParameterType()));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (Objects.equals(request.getHeaders().getFirst(ParamConstant.INSIDE), "true")) {
            log.debug("服务间调用，返回数据不包装 R 对象");
            return body;
        }
        log.debug("返回数据包装 R 对象");
        return R.ok(body);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
