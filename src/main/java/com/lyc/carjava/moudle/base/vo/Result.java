package com.lyc.carjava.moudle.base.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "返回结果",description = "统一返回类")
public class Result<T> {
    private String code;
    private String result;
    private Object message;

    public static Result ERROR(String code)
    {
        Result r = new Result();
        r.setCode(code);
        r.setResult("fail");
        r.setMessage("null");
        return r;
    }

    public static Result OK(Object message)
    {
        Result r = new Result();
        r.setCode("0000");
        r.setMessage(message);
        r.setResult("success");
        return r;
    }
}
