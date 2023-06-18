package com.niceflow.component.mongo.utils;

import com.niceflow.component.common.page.DefaultPageResponse;
import com.niceflow.component.common.page.PageRequest;
import com.niceflow.component.common.page.PageResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

/**
 * 分页查询
 *
 * @author duanjw
 * @date 2023/3/29
 */
@UtilityClass
public class PageUtil {

    public static org.springframework.data.domain.PageRequest convert(PageRequest pageRequest) {
        return org.springframework.data.domain.PageRequest.of(pageRequest.getPageIndex() - 1, pageRequest.getPageSize());
    }

    /**
     * @param page 响应
     * @param <T>  T
     * @return 分页响应
     */
    public static <T> PageResponse<T> convert(Page<T> page) {
        long count = page.getTotalElements();
        int pageIndex = page.getNumber() + 1;
        if (count == 0) {
            return new DefaultPageResponse<>(pageIndex, page.getSize());
        }
        return new DefaultPageResponse<>(page.getContent(), count, pageIndex, page.getSize());
    }
}
