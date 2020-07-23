package com.lyc.carjava.modules.base.service;

import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.modules.app.vo.CanBorrowCarVo;
import com.lyc.carjava.modules.base.dto.PageDto;
import com.lyc.carjava.modules.base.entity.Car;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.carjava.modules.system.dto.CarDto;
import com.lyc.carjava.modules.system.dto.IdDto;
import com.lyc.carjava.modules.system.vo.DamageCarVo;
import com.lyc.carjava.modules.system.vo.UsefulCarVo;

import java.util.List;

/**
 * <p>
 * 电动车 服务类
 * </p>
 *
 * @author lyc
 * @since 2020-06-20
 */
public interface CarService extends IService<Car> {
    public void borrowCar(String userId,String carId) throws BizException;

    public void returnCar(String userId,String carId,String address) throws BizException;

    public List<UsefulCarVo> usefulcarList(PageDto pageDto);

    public List<DamageCarVo> damagecarList(PageDto pageDto);

    public void addcar(CarDto carDto) throws BizException;

    public void delcar(IdDto idDto) throws BizException;

    public List<CanBorrowCarVo> canBorrowedCar(PageDto pageDto);
}
