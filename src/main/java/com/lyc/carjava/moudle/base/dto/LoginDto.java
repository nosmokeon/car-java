package com.lyc.carjava.moudle.base.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "登录参数",description = "请求参数")
public class LoginDto {
    private String userName;
    private String password;
}
