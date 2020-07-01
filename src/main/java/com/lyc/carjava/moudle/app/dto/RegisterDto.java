package com.lyc.carjava.moudle.app.dto;

import lombok.Data;

@Data
public class RegisterDto {

    /**
     * 姓名
     */
    private String userName;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 驾驶证
     */
    private String driverId;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 密码
     */
    private String password;


}
