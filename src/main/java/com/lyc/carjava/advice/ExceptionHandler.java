package com.lyc.carjava.advice;


import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.modules.base.vo.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result exception(Exception e)
    {
        return Result.ERROR(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BizException.class)
    public Result bizException(BizException e){return Result.ERROR(e.getMessage());}

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validException(MethodArgumentNotValidException e)
    {
        StringBuilder stringBuilder = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        for (ObjectError error:bindingResult.getAllErrors())
        {
            stringBuilder.append(error.getDefaultMessage());
        }
        return Result.ERROR(stringBuilder.toString());
    }


}
