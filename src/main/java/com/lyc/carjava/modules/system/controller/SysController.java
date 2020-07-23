package com.lyc.carjava.modules.system.controller;

import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.modules.base.dto.LoginDto;
import com.lyc.carjava.modules.base.dto.PageDto;
import com.lyc.carjava.modules.base.service.AdminService;
import com.lyc.carjava.modules.base.service.CarService;
import com.lyc.carjava.modules.base.vo.Result;
import com.lyc.carjava.modules.system.dto.CarDto;
import com.lyc.carjava.modules.system.dto.IdDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys")
@Slf4j
@Api(tags = "管理员接口")
@CrossOrigin
public class SysController {
    @Autowired
    AdminService adminService;

    @Autowired
    CarService carService;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "管理员登录",httpMethod = "POST")
    public Result login(@RequestBody LoginDto loginDto) throws BizException {
        return Result.OK(adminService.login(loginDto));
    }

    @RequestMapping(value = "/usefulcars",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "有效车列表",httpMethod = "POST")
    public Result usefulcars(@RequestBody PageDto pageDto)  {
        return Result.OK(carService.usefulcarList(pageDto));
    }

    @RequestMapping(value = "/damagecars",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "故障车列表",httpMethod = "POST")
    public Result damagecars(@RequestBody PageDto pageDto)  {
        return Result.OK(carService.damagecarList(pageDto));
    }

    @RequestMapping(value = "/addcar",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加新车",httpMethod = "POST")
    public Result addcar(@RequestBody CarDto carDto) throws BizException {
        carService.addcar(carDto);
        return Result.OK(null);
    }

    @RequestMapping(value = "/delcar",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除车辆",httpMethod = "POST")
    public Result delcar(@RequestBody IdDto idDto) throws BizException {
        carService.delcar(idDto);
        return Result.OK(null);
    }


}
