package com.lyc.carjava.moudle.base.vo;

import lombok.Data;

@Data
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
        r.setMessage(message);
        r.setResult("success");
        return r;
    }
}
