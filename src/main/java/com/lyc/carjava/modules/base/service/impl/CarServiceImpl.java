package com.lyc.carjava.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.modules.base.dto.PageDto;
import com.lyc.carjava.modules.base.entity.Car;
import com.lyc.carjava.modules.base.entity.CarRepairRecord;
import com.lyc.carjava.modules.base.entity.CarUseRecord;
import com.lyc.carjava.modules.base.enums.CarDestoriedEnum;
import com.lyc.carjava.modules.base.enums.CarRepairStateEnum;
import com.lyc.carjava.modules.base.enums.CarUseStateEnum;
import com.lyc.carjava.modules.base.enums.DelFlagEnum;
import com.lyc.carjava.modules.base.mapper.CarMapper;
import com.lyc.carjava.modules.base.mapper.CarRepairRecordMapper;
import com.lyc.carjava.modules.base.mapper.CarUseRecordMapper;
import com.lyc.carjava.modules.base.mapper.UserMapper;
import com.lyc.carjava.modules.base.service.CarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.carjava.modules.system.dto.CarDto;
import com.lyc.carjava.modules.system.dto.IdDto;
import com.lyc.carjava.modules.system.vo.DamageCarVo;
import com.lyc.carjava.modules.system.vo.UsefulCarVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 电动车 服务实现类
 * </p>
 *
 * @author lyc
 * @since 2020-06-20
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {
    @Resource
    UserMapper userMapper;

    @Resource
    CarMapper carMapper;

    @Resource
    CarUseRecordMapper carUseRecordMapper;

    @Resource
    CarRepairRecordMapper repairRecordMapper;

    public CarServiceImpl() {
        super();
    }

    /**
     * 借车接口
     *先判断用户是否有车未还和车辆是否能够使用
     * 再判断车辆是否未被使用
     * 最后判断车辆是否在被维修
     * @param userId
     * @param carId
     * @throws BizException
     */
    @Override
    public void borrowCar(String userId, String carId) throws BizException {
        QueryWrapper<CarUseRecord> carUseRecordQueryWrapper = new QueryWrapper<>();
        carUseRecordQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
        carUseRecordQueryWrapper.eq("USER_ID",userId);
        carUseRecordQueryWrapper.orderByDesc("CREATE_TIME");
        List<CarUseRecord> records = carUseRecordMapper.selectPage(new Page<>(1,2),carUseRecordQueryWrapper).getRecords();
        if(records == null || records.isEmpty() || records.get(0).getUseState().equals(CarUseStateEnum.RETURN.getValue()))
        {
            Car car = carMapper.selectById(carId);
            if(car ==null || car.getDelFlag().equals(DelFlagEnum.DEL.getValue()))
            {
                throw new BizException("车辆不存在");
            }
            else if (car.getIsDestroied().equals(CarDestoriedEnum.DESTORIED.getValue()))
            {
                throw new BizException("车辆已报废");
            }
            else if (car.getRemianPower() <= 10)
            {
                throw new BizException("车辆电量小于10%，请选择其他车辆");
            }
            QueryWrapper<CarUseRecord> carUseRecordQueryWrapper1 = new QueryWrapper<>();
            carUseRecordQueryWrapper1.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
            carUseRecordQueryWrapper1.eq("CAR_ID",carId);
            carUseRecordQueryWrapper.orderByDesc("CREATE_TIME");
            List<CarUseRecord> carUseRecords = carUseRecordMapper.selectPage(new Page<>(1,2),carUseRecordQueryWrapper1).getRecords();
            if(carUseRecords != null && !carUseRecords.isEmpty() &&carUseRecords.get(0).getUseState().equals(CarUseStateEnum.NOT_RETURN.getValue()))
            {
                throw new BizException("车辆已经被占用");
            }

            QueryWrapper<CarRepairRecord> repairRecordQueryWrapper = new QueryWrapper<>();
            repairRecordQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
            repairRecordQueryWrapper.eq("CAR_ID",carId);
            repairRecordQueryWrapper.orderByDesc("CREATE_TIME");
            List<CarRepairRecord> repairRecords = repairRecordMapper.selectPage(new Page<>(1,2),repairRecordQueryWrapper).getRecords();
            if(repairRecords !=null && !repairRecords.isEmpty() && !repairRecords.get(0).getRepairState().equals(CarRepairStateEnum.OUT.getValue()))
            {
                throw new BizException("汽车正在维修");
            }

            CarUseRecord record = new CarUseRecord();
            record.setCarId(carId);
            record.setUserId(userId);
            record.setUseState(CarUseStateEnum.NOT_RETURN.getValue());
            record.setStartTime(LocalDateTime.now());
            record.setStartPlace(car.getNowPlace());
            carUseRecordMapper.insert(record);
        }
        else
        {
            throw new BizException("你还没还车");
        }


    }

    /**
     * 还车接口
     * @param userId
     * @param carId
     * @throws BizException
     */
    @Override
    public void returnCar(String userId, String carId,String address) throws BizException {
        QueryWrapper<CarUseRecord> carUseRecordQueryWrapper = new QueryWrapper<>();
        carUseRecordQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
        carUseRecordQueryWrapper.eq("CAR_ID",carId);
        carUseRecordQueryWrapper.orderByDesc("CREATE_TIME");
        List<CarUseRecord> records = carUseRecordMapper.selectPage(new Page<>(1,2),carUseRecordQueryWrapper).getRecords();
        if(records == null || records.isEmpty() || records.get(0).getUseState().equals(CarUseStateEnum.RETURN.getValue()))
        {
            throw new BizException("车辆未被借出或者车辆已还");
        }
        else if(!records.get(0).getUserId().equals(userId))
        {
            throw new BizException("你不是借车人");
        }
        else
        {
            CarUseRecord record = records.get(0);
            record.setUseState(CarUseStateEnum.RETURN.getValue());
            record.setEndTime(LocalDateTime.now());
            record.setEndPlace(address);
            Car car = carMapper.selectById(carId);
            car.setNowPlace(address);
            carMapper.updateById(car);
            carUseRecordMapper.updateById(record);
        }
    }

    @Override
    public List<UsefulCarVo> usefulcarList(PageDto pageDto) {
        int current = (pageDto.getCurrent()-1)*pageDto.getPageSize();
        return carMapper.usefulcars(current,pageDto.getPageSize());
    }

    @Override
    public List<DamageCarVo> damagecarList(PageDto pageDto) {
        int current = (pageDto.getCurrent()-1)*pageDto.getPageSize();
        return carMapper.damagecars(current,pageDto.getPageSize());
    }

    @Override
    public void addcar(CarDto carDto) throws BizException {
        QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
        carQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
        carQueryWrapper.eq("CAR_NUMBER",carDto.getCarNumber());
        carQueryWrapper.eq("VIN",carDto.getVin());
        int count = carMapper.selectCount(carQueryWrapper);
        if(count > 0)
        {
            throw new BizException("the car is existed!");
        }
        Car car = new Car();
        BeanUtils.copyProperties(carDto,car);
        carMapper.insert(car);
    }

    @Override
    public void delcar(IdDto idDto) throws BizException {
        QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
        carQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
        carQueryWrapper.eq("ID",idDto.getId());
        Car car = carMapper.selectOne(carQueryWrapper);
        if(car == null)
        {
            throw new BizException("the car is not existed !");
        }
        car.setDelFlag(DelFlagEnum.DEL.getValue());
        carMapper.updateById(car);
    }
}
