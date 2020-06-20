package com.lyc.carjava.advice.exceptions;

import lombok.Data;

@Data
public class BizException extends Exception {
    private String message;
    public BizException(String message) {this.message = message;}
}
