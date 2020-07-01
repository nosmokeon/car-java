package com.lyc.carjava.moudle.system.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "id参数",description = "请求参数")
public class IdDto {
    String id;
}
