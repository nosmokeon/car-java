package com.lyc.carjava.moudle.system.controller;

import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.moudle.base.dto.LoginDto;
import com.lyc.carjava.moudle.base.service.AdminService;
import com.lyc.carjava.moudle.base.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
@Slf4j
public class SysController {
    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody LoginDto loginDto) throws BizException {
        return Result.OK(adminService.login(loginDto));
    }
}
