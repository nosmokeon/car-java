package com.lyc.carjava.moudle.system.vo;

import lombok.Data;

@Data
public class UsefulCarVo {
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

    /**
     * 使用人
     */
    private String userName;

    /**
     * 使用人身份证号
     */
    private String idCard;
}
