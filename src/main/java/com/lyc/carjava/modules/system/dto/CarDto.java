package com.lyc.carjava.modules.system.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "新车参数",description = "请求参数")
public class CarDto {

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 车型
     */
    private String carType;

    /**
     * 车辆识别代号
     */
    private String vin;

    /**
     * 是否报废
     */
    private Integer isDestroied;

    /**
     * 现在所在地
     */
    private String nowPlace;

    /**
     * 剩余电量
     */
    private Integer remianPower;
}
