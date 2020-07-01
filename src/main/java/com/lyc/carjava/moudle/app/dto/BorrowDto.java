package com.lyc.carjava.moudle.app.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "借车参数",description = "请求参数")
public class BorrowDto {
    private String carId;
}
