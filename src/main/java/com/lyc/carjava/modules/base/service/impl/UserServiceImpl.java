package com.lyc.carjava.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.modules.app.dto.RegisterDto;
import com.lyc.carjava.modules.base.dto.LoginDto;
import com.lyc.carjava.modules.base.entity.User;
import com.lyc.carjava.modules.base.enums.DelFlagEnum;
import com.lyc.carjava.modules.base.mapper.UserMapper;
import com.lyc.carjava.modules.base.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.carjava.modules.base.vo.TokenVo;
import com.lyc.carjava.util.JWTUtil;
import com.lyc.carjava.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 普通用户 服务实现类
 * </p>
 *
 * @author lyc
 * @since 2020-06-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    RedisUtil redisUtil;

    @Resource
    UserMapper userMapper;



    @Override
    public void register(RegisterDto registerDto) throws BizException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("PHONE",registerDto.getPhone());
        userQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
        User u = userMapper.selectOne(userQueryWrapper);
        if(u != null)
        {
            throw new BizException("user has existed");
        }

        User user = new User();
        BeanUtils.copyProperties(registerDto,user);
        userMapper.insert(user);
    }

    @Override
    public TokenVo login(LoginDto loginDto) throws BizException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("PHONE",loginDto.getUserName());
        userQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null || !user.getPassword().equals(loginDto.getPassword()))
        {
            throw new BizException("null user or wrong password");
        }
        else
        {
            TokenVo tokenVo = new TokenVo();
            tokenVo.setToken(JWTUtil.sign(user.getId(),JWTUtil.secret));
            redisUtil.set(user.getId(),tokenVo.getToken());
            redisUtil.expire(user.getId(),RedisUtil.EXPIRE_TIME);
            return tokenVo;
        }

    }
}
