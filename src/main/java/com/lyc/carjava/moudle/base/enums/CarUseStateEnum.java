package com.lyc.carjava.moudle.base.enums;

public enum CarUseStateEnum {
    RETURN("已还","0"),NOT_RETURN("未还","1");

    private String code;
    private String value;

    private CarUseStateEnum(String code, String value) {
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
