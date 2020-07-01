package com.lyc.carjava.modules.base.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "分页参数",description = "请求参数")
public class PageDto {
    private int current;
    private int pageSize;
}
