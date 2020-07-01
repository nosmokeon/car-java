package com.lyc.carjava.modules.base.service;

import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.modules.app.dto.RegisterDto;
import com.lyc.carjava.modules.base.dto.LoginDto;
import com.lyc.carjava.modules.base.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.carjava.modules.base.vo.TokenVo;

/**
 * <p>
 * 普通用户 服务类
 * </p>
 *
 * @author lyc
 * @since 2020-06-20
 */
public interface UserService extends IService<User> {
    public void register(RegisterDto registerDto) throws BizException;

    public TokenVo login(LoginDto loginDto) throws BizException;


}
