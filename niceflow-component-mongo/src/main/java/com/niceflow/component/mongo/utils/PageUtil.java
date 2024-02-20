package com.niceflow.component.mongo.utils;

import com.niceflow.component.common.page.DefaultPageResponse;
import com.niceflow.component.common.page.PageRequest;
import com.niceflow.component.common.page.PageResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.List;

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
     * 转换 page 为 PageResponse
     *
     * @param page 响应
     * @param <T>  T
     * @return 分页响应
     */
    public static <T> PageResponse<T> convert(Page<T> page) {
        return convert(page, page.getContent());
    }

    /**
     * 转换 page 为 PageResponse
     *
     * @param page 响应
     * @param t    数据 不使用 page 里的数据
     * @param <T>  T
     * @return 分页响应
     */
    public static <T> PageResponse<T> convert(Page<?> page, List<T> t) {
        long count = page.getTotalElements();
        int pageIndex = page.getNumber() + 1;
        if (count == 0) {
            return new DefaultPageResponse<>(pageIndex, page.getSize());
        }
        return new DefaultPageResponse<>(t, count, pageIndex, page.getSize());
    }
}
