package com.lyc.carjava.modules.app.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "还车参数",description = "请求参数")
public class ReturnDto {
    private String carId;
    private String address;
}
