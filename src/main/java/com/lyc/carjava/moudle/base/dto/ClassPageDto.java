package com.lyc.carjava.moudle.base.dto;

import lombok.Data;

@Data
public class ClassPageDto {
    private String collegeName;
    private String subjectName;
    private String className;
    private int current;
    private int pageSize;
}
