package com.lyc.carjava.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.modules.app.vo.CanBorrowCarVo;
import com.lyc.carjava.modules.base.dto.PageDto;
import com.lyc.carjava.modules.base.entity.*;
import com.lyc.carjava.modules.base.enums.CarDestoriedEnum;
import com.lyc.carjava.modules.base.enums.CarRepairStateEnum;
import com.lyc.carjava.modules.base.enums.CarUseStateEnum;
import com.lyc.carjava.modules.base.enums.DelFlagEnum;
import com.lyc.carjava.modules.base.mapper.*;
import com.lyc.carjava.modules.base.service.CarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.carjava.modules.system.dto.CarDto;
import com.lyc.carjava.modules.system.dto.IdDto;
import com.lyc.carjava.modules.system.vo.DamageCarVo;
import com.lyc.carjava.modules.system.vo.UsefulCarVo;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    CarTrafficViolationRecordMapper trafficViolationRecordMapper;

    @Async
    public void insertCarRecord() {
        for (int i = 0; i <= 10 * 10000; i++) {
            Car car = new Car();
            car.setCarNumber((i + 10000) + "");
            //车型
            if (i % 2 == 0 && i % 3 != 0) {
                car.setCarType("兰博基尼");
            } else if(i%2 != 0 && i%3 ==0){
                car.setCarType("劳斯莱斯");
            }
            else if(i % 2 == 0 && i % 3 == 0){
                car.setCarType("保时捷");
            }
            else{
                car.setCarType("迈凯伦");
            }
            //车地点
            if (i % 4 == 0 && i % 5 != 0) {
                car.setNowPlace("浙江科技学院");
            } else if(i%4 != 0 && i%5 ==0){
                car.setNowPlace("宁波万里学院");
            }
            else if(i % 4 == 0 && i % 5 == 0){
                car.setNowPlace("大连名族学院");
            }
            else{
                car.setNowPlace("北京五道口职业技术学院");
            }

            //vin
            car.setVin(""+(i+100));

            //vim
            car.setRemianPower((int)(Math.random()*101));

            carMapper.insert(car);

        }
    }
    @Async
    public void insertUserRecord()
    {
        for(int i =0 ;i<=10000;i++){
            User user = new User();
            user.setDriverId(""+(i+1000));
            user.setIdCard(""+(i+1000));
            user.setPassword("123456");
            user.setPhone(""+(i+10000));
            user.setUserName("李华"+System.nanoTime());
            userMapper.insert(user);
        }
    }

    public void insertTrafficRecord()
    {
        for(int i =0 ;i<=10000;i++){
            if(i%2 == 0)
            {
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("DRIVER_ID",""+(i+1000));
                User u = userMapper.selectOne(userQueryWrapper);

                QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
                carQueryWrapper.eq("CAR_NUMBER",(i + 10000) + "");
                Car car = carMapper.selectOne(carQueryWrapper);

                CarTrafficViolationRecord record = new CarTrafficViolationRecord();
                record.setCarId(car.getId());
                record.setUserId(u.getId());
                trafficViolationRecordMapper.insert(record);

            }
        }
    }


    public CarServiceImpl() {
        super();
    }

    /**
     * 借车接口
     * 先判断用户是否有车未还和车辆是否能够使用
     * 再判断车辆是否未被使用
     * 最后判断车辆是否在被维修
     *
     * @param userId
     * @param carId
     * @throws BizException
     */
    @Override
    public void borrowCar(String userId, String carId) throws BizException {
        QueryWrapper<CarUseRecord> carUseRecordQueryWrapper = new QueryWrapper<>();
        carUseRecordQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
        carUseRecordQueryWrapper.eq("USER_ID", userId);
        carUseRecordQueryWrapper.orderByDesc("CREATE_TIME");
        List<CarUseRecord> records = carUseRecordMapper.selectPage(new Page<>(1, 2), carUseRecordQueryWrapper).getRecords();
        if (records == null || records.isEmpty() || records.get(0).getUseState().equals(CarUseStateEnum.RETURN.getValue())) {
            Car car = carMapper.selectById(carId);
            if (car == null || car.getDelFlag().equals(DelFlagEnum.DEL.getValue())) {
                throw new BizException("车辆不存在");
            } else if (car.getIsDestroied().equals(CarDestoriedEnum.DESTORIED.getValue())) {
                throw new BizException("车辆已报废");
            } else if (car.getRemianPower() <= 10) {
                throw new BizException("车辆电量小于10%，请选择其他车辆");
            }
            QueryWrapper<CarUseRecord> carUseRecordQueryWrapper1 = new QueryWrapper<>();
            carUseRecordQueryWrapper1.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
            carUseRecordQueryWrapper1.eq("CAR_ID", carId);
            carUseRecordQueryWrapper.orderByDesc("CREATE_TIME");
            List<CarUseRecord> carUseRecords = carUseRecordMapper.selectPage(new Page<>(1, 2), carUseRecordQueryWrapper1).getRecords();
            if (carUseRecords != null && !carUseRecords.isEmpty() && carUseRecords.get(0).getUseState().equals(CarUseStateEnum.NOT_RETURN.getValue())) {
                throw new BizException("车辆已经被占用");
            }

            QueryWrapper<CarRepairRecord> repairRecordQueryWrapper = new QueryWrapper<>();
            repairRecordQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
            repairRecordQueryWrapper.eq("CAR_ID", carId);
            repairRecordQueryWrapper.orderByDesc("CREATE_TIME");
            List<CarRepairRecord> repairRecords = repairRecordMapper.selectPage(new Page<>(1, 2), repairRecordQueryWrapper).getRecords();
            if (repairRecords != null && !repairRecords.isEmpty() && !repairRecords.get(0).getRepairState().equals(CarRepairStateEnum.OUT.getValue())) {
                throw new BizException("汽车正在维修");
            }

            CarUseRecord record = new CarUseRecord();
            record.setCarId(carId);
            record.setUserId(userId);
            record.setUseState(CarUseStateEnum.NOT_RETURN.getValue());
            record.setStartTime(LocalDateTime.now());
            record.setStartPlace(car.getNowPlace());
            carUseRecordMapper.insert(record);
        } else {
            throw new BizException("你还没还车");
        }


    }

    /**
     * 还车接口
     *
     * @param userId
     * @param carId
     * @throws BizException
     */
    @Override
    public void returnCar(String userId, String carId, String address) throws BizException {
        QueryWrapper<CarUseRecord> carUseRecordQueryWrapper = new QueryWrapper<>();
        carUseRecordQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
        carUseRecordQueryWrapper.eq("CAR_ID", carId);
        carUseRecordQueryWrapper.orderByDesc("CREATE_TIME");
        List<CarUseRecord> records = carUseRecordMapper.selectPage(new Page<>(1, 2), carUseRecordQueryWrapper).getRecords();
        if (records == null || records.isEmpty() || records.get(0).getUseState().equals(CarUseStateEnum.RETURN.getValue())) {
            throw new BizException("车辆未被借出或者车辆已还");
        } else if (!records.get(0).getUserId().equals(userId)) {
            throw new BizException("你不是借车人");
        } else {
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
        int current = (pageDto.getCurrent() - 1) * pageDto.getPageSize();
        return carMapper.usefulcars(current, pageDto.getPageSize());
    }

    @Override
    public List<DamageCarVo> damagecarList(PageDto pageDto) {
        int current = (pageDto.getCurrent() - 1) * pageDto.getPageSize();
        return carMapper.damagecars(current, pageDto.getPageSize());
    }

    @Override
    @Async
    public void addcar(CarDto carDto) throws BizException {
//        QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
//        carQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
//        carQueryWrapper.eq("CAR_NUMBER",carDto.getCarNumber());
//        carQueryWrapper.eq("VIN",carDto.getVin());
//        int count = carMapper.selectCount(carQueryWrapper);
//        if(count > 0)
//        {
//            throw new BizException("the car is existed!");
//        }
//        Car car = new Car();
//        BeanUtils.copyProperties(carDto,car);
//        carMapper.insert(car);
        insertTrafficRecord();
    }

    @Override
    public void delcar(IdDto idDto) throws BizException {
        QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
        carQueryWrapper.eq("DEL_FLAG", DelFlagEnum.NOT_DEL.getValue());
        carQueryWrapper.eq("ID", idDto.getId());
        Car car = carMapper.selectOne(carQueryWrapper);
        if (car == null) {
            throw new BizException("the car is not existed !");
        }
        car.setDelFlag(DelFlagEnum.DEL.getValue());
        carMapper.updateById(car);
    }

    @Override
    public List<CanBorrowCarVo> canBorrowedCar(PageDto pageDto) {
        int current = (pageDto.getCurrent() - 1) * pageDto.getPageSize();
        return carMapper.canBorrowedCars(current, pageDto.getPageSize());
    }
}
