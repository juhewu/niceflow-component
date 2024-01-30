package com.niceflow.component.spring.plus.feign;

import com.niceflow.component.spring.plus.constants.ParamConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务间请求头封装
 *
 * @author duanjw
 * @date 2023/1/30
 */
@Slf4j
public class FeignHeaderWrapInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        template.header(ParamConstant.INSIDE, "true");
    }
}