package com.lyc.carjava.moudle.system.vo;

import lombok.Data;

@Data
public class CarVo {
    /**
     * ID
     */
    private String id;

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
    private String isDestroied;

    /**
     * 现在所在地
     */
    private String nowPlace;

    /**
     * 剩余电量
     */
    private Integer remianPower;

    /**
     * 是否正在使用
     */
    private String is_used;

    /**
     * 使用人
     */
    private String userName;
}
