package com.lyc.carjava.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.modules.base.dto.LoginDto;
import com.lyc.carjava.modules.base.entity.Admin;
import com.lyc.carjava.modules.base.enums.DelFlagEnum;
import com.lyc.carjava.modules.base.mapper.AdminMapper;
import com.lyc.carjava.modules.base.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.carjava.modules.base.vo.TokenVo;
import com.lyc.carjava.util.JWTUtil;
import com.lyc.carjava.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author lyc
 * @since 2020-06-20
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    AdminMapper adminMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public TokenVo login(LoginDto loginDto) throws BizException {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("PHONE",loginDto.getUserName());
        adminQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
        Admin admin = adminMapper.selectOne(adminQueryWrapper);
        if(admin == null || !admin.getPassword().equals(loginDto.getPassword()))
        {
            throw new BizException("null user or wrong password");
        }
        else
        {
            TokenVo tokenVo = new TokenVo();
            tokenVo.setToken(JWTUtil.sign(admin.getId(),JWTUtil.secret));
            redisUtil.set(admin.getId(),tokenVo.getToken());
            redisUtil.expire(admin.getId(), RedisUtil.EXPIRE_TIME);
            return tokenVo;
        }
    }
}
