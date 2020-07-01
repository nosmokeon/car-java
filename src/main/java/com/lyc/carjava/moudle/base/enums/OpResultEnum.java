package com.lyc.carjava.moudle.base.enums;

public enum OpResultEnum {
    NOT_OP("未处理","0"),FAIL("fail","1"),SUEESS("success","2");

    private String code;
    private String value;

    private  OpResultEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
