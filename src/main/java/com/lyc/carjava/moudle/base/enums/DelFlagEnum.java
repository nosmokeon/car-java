package com.lyc.carjava.moudle.base.enums;

public enum DelFlagEnum {
    DEL("delete","1"),NOT_DEL("normal","0");

    private String code;
    private String value;

    private DelFlagEnum(String code, String value) {
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
