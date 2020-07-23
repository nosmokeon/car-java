package com.lyc.carjava.modules.app.vo;

import lombok.Data;

@Data
public class CanBorrowCarVo {
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
    private String isUsed;
}
