package com.lyc.carjava.moudle.system.vo;

import lombok.Data;

@Data
public class DamageCarVo {
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
    private int isDestroied;

    /**
     * 现在所在地
     */
    private String nowPlace;

    /**
     * 剩余电量
     */
    private Integer remianPower;


    /**
     * 维修人
     */
    private String userName;

    /**
     * 维修人身份证
     */
    private String idCard;
}
