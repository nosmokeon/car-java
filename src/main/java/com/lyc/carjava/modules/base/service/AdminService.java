package com.lyc.carjava.modules.base.service;

import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.modules.base.dto.LoginDto;
import com.lyc.carjava.modules.base.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.carjava.modules.base.vo.TokenVo;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author lyc
 * @since 2020-06-20
 */
public interface AdminService extends IService<Admin> {
    public TokenVo login(LoginDto loginDto) throws BizException;
}
