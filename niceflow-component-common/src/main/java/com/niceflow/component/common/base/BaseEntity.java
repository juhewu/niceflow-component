package com.niceflow.component.common.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体
 *
 * @author duanjw
 * @date 2023/2/17
 */
@Getter
@Setter
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 最后修改时间
     */
    private LocalDateTime updatedAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 最后修改时间
     */
    private String updatedBy;
}
