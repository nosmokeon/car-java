package com.lyc.carjava.moudle.app.controller;

import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.moudle.app.dto.RegisterDto;
import com.lyc.carjava.moudle.base.dto.LoginDto;
import com.lyc.carjava.moudle.base.service.UserService;
import com.lyc.carjava.moudle.base.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
@Slf4j
public class AppController {

    @Autowired
    UserService userService;
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Result register(@RequestBody RegisterDto registerDto) throws BizException {
        userService.register(registerDto);
        return Result.OK(null);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody LoginDto loginDto) throws BizException {
        return Result.OK(userService.login(loginDto));
    }


}
