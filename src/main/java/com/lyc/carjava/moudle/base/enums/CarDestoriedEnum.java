package com.lyc.carjava.moudle.base.enums;

public enum CarDestoriedEnum {

    DESTORIED("报废","0"),NORMAL("正常","1");

    private String code;
    private String value;

    private  CarDestoriedEnum(String code, String value) {
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
