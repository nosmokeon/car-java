package com.lyc.carjava.modules.base.enums;

public enum CarRepairStateEnum {
    REPAIR("正在维修","1"),NORMAL("修好","0"),OUT("出厂","2");

    private String code;
    private String value;

    private  CarRepairStateEnum(String code, String value) {
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
