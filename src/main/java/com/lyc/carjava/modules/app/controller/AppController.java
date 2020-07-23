package com.lyc.carjava.modules.app.controller;

import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.modules.app.dto.BorrowDto;
import com.lyc.carjava.modules.app.dto.RegisterDto;
import com.lyc.carjava.modules.app.dto.ReturnDto;
import com.lyc.carjava.modules.base.dto.LoginDto;
import com.lyc.carjava.modules.base.dto.PageDto;
import com.lyc.carjava.modules.base.service.CarService;
import com.lyc.carjava.modules.base.service.UserService;
import com.lyc.carjava.modules.base.vo.Result;
import com.lyc.carjava.resolver.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/app")
@Slf4j
@Api(tags = "用户接口")
@CrossOrigin
public class AppController {

    @Autowired
    UserService userService;

    @Autowired
    CarService carService;



    @ApiOperation(value = "用户注册",httpMethod = "POST")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Result register(@RequestBody RegisterDto registerDto) throws BizException {
        userService.register(registerDto);
        return Result.OK(null);
    }

    @ApiOperation(value = "用户登录",httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login( @RequestBody LoginDto loginDto) throws BizException {
        return Result.OK(userService.login(loginDto));
    }

    @ApiOperation(value = "借车",httpMethod = "POST")
    @RequestMapping(value = "/borrow",method = RequestMethod.POST)
    @ResponseBody
    public Result borrowCar(@RequestBody BorrowDto borrowDto, @ApiIgnore UserInfo userInfo) throws BizException {
        carService.borrowCar(userInfo.getUserId(),borrowDto.getCarId());
        return Result.OK(null);
    }

    @ApiOperation(value = "还车",httpMethod = "POST")
    @RequestMapping(value = "/return",method = RequestMethod.POST)
    @ResponseBody
    public Result rreturnCar(@RequestBody ReturnDto returnDto, @ApiIgnore UserInfo userInfo) throws BizException {
        carService.returnCar(userInfo.getUserId(),returnDto.getCarId(),returnDto.getAddress());
        return Result.OK(null);
    }

    @ApiOperation(value = "可用车列表",httpMethod = "POST")
    @RequestMapping(value = "/carlist",method = RequestMethod.POST)
    @ResponseBody
    public Result carlist(@RequestBody PageDto pageDto, @ApiIgnore UserInfo userInfo) throws BizException {
        return Result.OK(carService.canBorrowedCar(pageDto));
    }

}
